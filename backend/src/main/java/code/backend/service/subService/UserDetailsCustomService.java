package code.backend.service.subService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import code.backend.persitence.entities.Account;
import code.backend.persitence.model.UserDetailCustom;
import code.backend.persitence.repository.AccountRepository;
import code.backend.persitence.repository.RefreshTokenRepository;

@Service
public class UserDetailsCustomService implements UserDetailsService {
	@Autowired
	AccountRepository userRepository;
	@Autowired
	RefreshTokenRepository refreshTokenRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account user = userRepository.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + username));
		user.setListOfRefreshToken(refreshTokenRepository.getTokenListOfUser(user.getIdAccount()));
		return UserDetailCustom.build(user);
	}
}
