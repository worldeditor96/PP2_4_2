package web.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import web.config.handler.LoginSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    //private PasswordEncoder passwordEncoder;

    private final UserDetailsService userDetailsService; // сервис, с помощью которого тащим пользователя
    private final LoginSuccessHandler loginSuccessHandler; // класс, в котором описана логика перенаправления пользователей по ролям

    public SecurityConfig(@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService, LoginSuccessHandler loginSuccessHandler) {
        this.userDetailsService = userDetailsService;
        this.loginSuccessHandler = loginSuccessHandler;
    }

//    @Autowired
//    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
//        this.passwordEncoder = passwordEncoder;
//    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/login").anonymous()
                .antMatchers("/user/**").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .and()
                .formLogin()
                .successHandler(loginSuccessHandler)// Как это работает
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .and().csrf().disable();
    }

/*
//корзина товаров

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
                User.builder()
                        .username("admin")
                        .password("admin")
                        //.password(passwordEncoder().encode( "admin"))
                        .roles(EnumRole.ADMIN.name())
                        .build(),
                User.builder()
                        .username("user")
                        .password("user")
                        //.password(passwordEncoder().encode( "admin"))
                        .roles(EnumRole.USER.name())
                        .build()
        );
    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    //
//    private UserAuthService userAuthService;
//
//    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().withUser("ADMIN").password("ADMIN").roles("ADMIN");
//        auth.inMemoryAuthentication().withUser("User").password("User").roles("ADMIN");// Что-то сам написал для пробы
//        //auth.userDetailsService().
//    }
//
//


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                // указываем страницу с формой логина. К этой странице должны иметь доступ все
                .loginPage("/login")
                //указываем логику обработки при логине
                .successHandler(new LoginSuccessHandler())
                // указываем action с формы логина
                .loginProcessingUrl("/login")
                // Указываем параметры логина и пароля с формы логина
                .usernameParameter("username")
                .passwordParameter("password")
                // даем доступ к форме логина всем
                .permitAll();
//
        http.logout()
                // разрешаем делать логаут всем
                .permitAll()
                // указываем URL логаута
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                // указываем URL при удачном логауте
                .logoutSuccessUrl("/login?logout")
                //выклчаем кроссдоменную секьюрность (на этапе обучения неважна)
                .and().csrf().disable();

        http
                .csrf().disable()
                // делаем страницу регистрации недоступной для авторизированных пользователей
                .authorizeRequests()
                //страницы аутентификаци доступна всем
                .antMatchers("/").permitAll()

                //Доступ по ролям через get POST DELETE Делать доступ через GET


                .antMatchers(HttpMethod.GET, "/hello").hasAnyRole(EnumRole.ADMIN.name(),EnumRole.USER.name())
                .antMatchers(HttpMethod.GET, "/user/**").hasAnyRole(EnumRole.ADMIN.name(),EnumRole.USER.name())
                .antMatchers(HttpMethod.GET, "/admin/**").hasAnyRole(EnumRole.ADMIN.name())
                .antMatchers(HttpMethod.POST, "/admin/**").hasRole(EnumRole.ADMIN.name())
                .antMatchers(HttpMethod.DELETE, "/admin/**").hasRole(EnumRole.ADMIN.name())


                //Не в этом причина
//                .antMatchers("/admin/**").hasRole("ADMIN")
//                .antMatchers("/user/**").hasRole("USER")



                .antMatchers("/login").anonymous()
                // защищенные URL ВОТ ТУТ И ПИШИ КУДА ОТПРАВИТЬ ЗАРЕГАННОГОЧУВАКА ПОСЛЕ АВТОРИЗАЦИИ!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                //через ACCESS, но не понял я откуда он берет название ролей ADMIN и USER
                .antMatchers("/admin/**").access("hasRole('EnumRole.ADMIN.name()')")
                .antMatchers("/user/**").access("hasAnyRole('EnumRole.ADMIN.name()', 'EnumRole.USER.name()')")
//                .antMatchers("/admin/**").access("hasRole('ADMIN')")
//                .antMatchers("/user/**").access("hasAnyRole('ADMIN', 'USER')")

//
//                .antMatchers("/hello").access("hasAnyRole('ADMIN','USER')")
//                .antMatchers("/admin/**").access("hasAuthority('ADMIN')")
//                .antMatchers("/user/**").access("hasAuthority('USER')")
                .anyRequest().authenticated();


//                .antMatchers("/index").access("hasRole(EnumRole.ADMIN.name())")
//                .antMatchers("/hello").access("hasAnyRole(EnumRole.ADMIN.name(),EnumRole.USER.name())")
                //.anyRequest()
                //.authenticated();
                //.antMatchers("/index").access("hasAnyRole('ADMIN')").anyRequest().authenticated();
                //Может и не надо нижее


    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance();
//    }

 */


    @Bean
    public BCryptPasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder(12);
    }
}
