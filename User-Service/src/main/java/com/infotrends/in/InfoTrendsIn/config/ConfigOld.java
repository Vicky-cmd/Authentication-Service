package com.infotrends.in.InfoTrendsIn.config;

public class ConfigOld {
//
//
//    @Autowired
//    private PasswordEncoder encoder;
//
//    @Autowired
//    private AppUserDetailsService appUserDetailsService;
//
//    @Override
//    protected void configure(HttpSecurity http)
//            throws Exception {
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .addFilter(new JWTFilter(authenticationManager()))
//                .addFilter(new JWTVerifierFilter(), JWTFilter.class)
//                .authorizeRequests()
//                .antMatchers(HttpMethod.GET, "/api/v1/users").permitAll()
////                .antMatchers(HttpMethod.GET, "/api/v1/users/**").hasAuthority(AppPermissions.USER_READ.name())
//                .anyRequest()
//                .authenticated()
//                .and().httpBasic();
////    	http.authorizeRequests()
////			.anyRequest()
////			.permitAll();
//        http.csrf().disable();
//        http.headers().frameOptions().disable();
//
//    }
//
//
////    @Override
////    @Bean
////    protected UserDetailsService userDetailsService() {
////        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
////
////        userDetailsManager.createUser(User.builder().username("vignesh").password(encoder.encode("password"))
////                .roles(AppRoles.USER.name()).authorities(AppRoles.USER.getAuthorities()).build());
////        userDetailsManager.createUser(User.builder().username("vicky").password(encoder.encode("password"))
////                .roles(AppRoles.READONLY_USER.name()).authorities(AppRoles.READONLY_USER.getAuthorities()).build());
////        userDetailsManager.createUser(User.builder().username("admin").password(encoder.encode("password123"))
////                .roles(AppRoles.ADMIN.name()).authorities(AppRoles.ADMIN.getAuthorities()).build());
////
////        return userDetailsManager;
////    }
//
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(authenticationProvider());
//    }
//
//
//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//        daoAuthenticationProvider.setPasswordEncoder(encoder);
//        daoAuthenticationProvider.setUserDetailsService(appUserDetailsService);
//        return daoAuthenticationProvider;
//
//    }
}
