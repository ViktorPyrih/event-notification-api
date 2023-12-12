package ua.cdu.edu.vu.event.notification.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.cdu.edu.vu.event.notification.api.domain.ApiClient;
import ua.cdu.edu.vu.event.notification.api.mapper.ApiClientMapper;
import ua.cdu.edu.vu.event.notification.api.model.Role;
import ua.cdu.edu.vu.event.notification.api.repository.ApiClientRepository;

import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

@Service
@RequiredArgsConstructor
public class ApiClientService implements UserDetailsService {

    private final ApiClientRepository apiClientRepository;
    private final ApiClientMapper apiClientMapper;

    @Override
    @Transactional(readOnly = true)
    public ApiClient loadUserByUsername(String username) throws UsernameNotFoundException {
        return apiClientRepository.findById(username)
                        .map(apiClientMapper::convertToDto)
                        .orElseThrow(() -> new UsernameNotFoundException("Client with id: %s not found".formatted(username)));
    }

    @Cacheable("api-clients")
    @Transactional(readOnly = true)
    public Map<String, ApiClient> loadAllClients() {
        return apiClientRepository.findAllByRole(Role.CLIENT).stream()
                .map(apiClientMapper::convertToDto)
                .collect(toMap(ApiClient::clientId, Function.identity()));
    }
}
