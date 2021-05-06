package com.example.demo.service;

import com.example.demo.config.GetTheUser;
import com.example.demo.entity.Company;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;

import com.example.demo.payload.LoginDto;
import com.example.demo.payload.RegisterDto;
import com.example.demo.payload.Response;


import com.example.demo.repository.CompanyRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtFilter;
import com.example.demo.security.JwtProvider;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class AuthService implements UserDetailsService {

    final UserRepository userRepository;
    final PasswordEncoder passwordEncoder;
    final RoleRepository roleRepository;
    final JavaMailSender javaMailSender;
    final AuthenticationManager authenticationManager;
    final JwtProvider jwtProvider;
    final JwtFilter jwtFilter;
    final CompanyRepository companyRepository;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, JavaMailSender javaMailSender,
                       AuthenticationManager authenticationManager, JwtProvider jwtProvider, @Lazy JwtFilter jwtFilter, CompanyRepository companyRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.javaMailSender = javaMailSender;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.jwtFilter = jwtFilter;
        this.companyRepository = companyRepository;
    }

    GetTheUser getTheUser = new GetTheUser();

    /**
     * BU METHOD BAZAGA USERNI REGISTIRATSIYADAN O'TKAZISH UCHUN ISHLATILADI
     * BAZAGA USERNI SAQLAYDI VA UNGA TASDIQLASH CODINI YUBORADI
     *
     * @param registerDto
     * @return
     */
    //
    public Response userRegister(RegisterDto registerDto, HttpServletRequest httpServletRequest) {
        boolean existsByEmail = userRepository.existsByEmail(registerDto.getEmail());
        if (existsByEmail){
            return new Response("Bunday email bazada mavjud",false);
        }
        User user = new User();
        List<Integer> roleListId = registerDto.getRoleListId();
        UserDetails userDetails = jwtFilter.getUser(httpServletRequest);
        int role=1;
       //hozircha oodiyroq username berdik takrorlanishiga tekshirmadik
        String username="Director"+registerDto.getFirstName();
        //USER BAZADA BOR YOKI YO'QLIGI TEKSHIRILYABDI
        if (userDetails != null) {
            //USER BAZADAN OLINDI
            Optional<User> optionalUser = getTheUser.getCurrentAuditorUser();
            //USERNINIG ROLLARI OLINDI
            Set<Role> roleUser = optionalUser.get().getRoles();

            /**
             * //TIZIMDAN FOYDALANUVCHILARGA ROLE BIRIKTIRIB CHIQAMIZ BU YERDA DIRECTOR
             *             //BARCHA USERLARNI QO'SHISHI MUMKIN
             *             //MANAGER ESA DIRECTORDAN BOSHQA USERLARNI QO'SHA OLADI
             *             //QOLGAN USERLARNI FILIAL MANAGERLARI QO'SHISHI MUMKIN
             *             //CLIENT O'ZINI O'ZI QO'SHISHI MUMKIN UNGA CLIENT ROLI BERILADI
             *             //ROLE VA FIRSTNAME NI BIRICTIRIB UNGA USERNAME YASAB QAYTARILADI HOZIRCHA
             */

            for (Role roleId : roleUser) {
                for (Integer roleDto : roleListId) {
                    /**
                     * role id bir bo'lsa companiya directori bo'ladi va u 2-3-4-5-6- id rollarni qo'sha oladi faqat o'zining companiya
                     * id siga kelgan companiya id si teng bo'lsa qo'shishga tuhsat beriladi va qolgan menejerlar ham o'ziga biriktirilgan
                     * companiya va filiallar id si orqali shu companiyaga  boshqa hodimlarni qo'shishi mumkin, shu orqali shu kompaniya
                     * shu managerga tegishli ekanini tekshirildi hozircha.
                     */
                    boolean company=optionalUser.get().getCompany().getId()==registerDto.getCompanyId();
                       if (roleId.getId() == 1 && roleDto==2 && company) {
                        role = 2;
                           username="Manager"+registerDto.getFirstName();
                    }
                  else if (roleId.getId() == 1 && roleDto==3 && company) {
                        role = 3;
                           username="FILIAL_MANAGER"+registerDto.getFirstName();
                    }
                  else if (roleId.getId() == 1 && roleDto==4 && company) {
                        role = 4;
                           username="NUMBER_MANAGER"+registerDto.getFirstName();
                    }
                  else if (roleId.getId() == 1 && roleDto==5 && company) {
                        role = 5;
                           username="EMPLOYEE_MANAGER"+registerDto.getFirstName();
                    }
                  else if (roleId.getId() == 1 && roleDto==6 && company) {
                           role = 6;
                           username="EMPLOYEE"+registerDto.getFirstName();
                       }
                       else if (roleId.getId() == 2 && roleDto==3 && company) {
                           role = 3;
                           username="FILIAL_MANAGER"+registerDto.getFirstName();
                       }
                       else if (roleId.getId() == 2 && roleDto==4 && company) {
                           role = 4;
                           username="NUMBER_MANAGER"+registerDto.getFirstName();
                       } else if (roleId.getId() == 2 && roleDto==5) {
                           role = 5;
                           username="EMPLOYEE_MANAGER"+registerDto.getFirstName();
                       } else if (roleId.getId() == 2 && roleDto==6 && company) {
                           role = 6;
                           username="NUMBER_MANAGER"+registerDto.getFirstName();
                       } else if (roleId.getId() == 3 && roleDto==4 && company) {
                           role = 4;
                           username="EMPLOYEE"+registerDto.getFirstName();
                       }   else if (roleId.getId() == 3 && roleDto==2 && company) {
                           role = 2;
                           username="FILIAL_MANAGER"+registerDto.getFirstName();
                       } else if (roleId.getId() == 3 && roleDto==5 && company) {
                           role = 5;
                           username="EMPLOYEE_MANAGER"+registerDto.getFirstName();
                       } else if (roleId.getId() == 3 && roleDto==6 && company) {
                           role = 6;
                           username="EMPLOYEE"+registerDto.getFirstName();
                       }


                  else {return new Response("Not added",false);}
                }
            }
        }
        if (roleListId!=null) {
            for (Integer roleClient : roleListId) {
                if (roleClient == 7) {
                    role = 7;
                    user.setRoles(Collections.singleton(roleRepository.findById(role).get()));
                    user.setFirstName(registerDto.getFirstName());
                    user.setLastName(registerDto.getLastName());
                    user.setEmail(registerDto.getEmail());
                    user.setPassword(registerDto.getPassword());
                    String username2="Client"+registerDto.getFirstName();
                    user.setUsername(username2);
                    user.setGender(registerDto.isGender());
                    user.setPlaceOfBirth(registerDto.getPlaceOfBirth());
                    user.setDateOfBirth(registerDto.getDateOfBirth());
                    user.setNationality(registerDto.getNationality());
                    user.setType(registerDto.isType());
                    user.setPassport(registerDto.getPassport());
                    user.setCompany(companyRepository.findById(registerDto.getCompanyId()).get());
                    userRepository.save(user);
                    //bu yerda oddiy clientlarni ro'yhatga oldik emailiga habar yuborilmaydi
                    return new Response("Muafaqiyatli ro'yhatdan o'tdingiz Aakkonutingiz", true);

                }
            }
        }
        user.setRoles(Collections.singleton(roleRepository.findById(role).get()));
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setEmail(registerDto.getEmail());
        String newPassword=UUID.randomUUID().toString().substring(1);
        user.setPassword(newPassword);
        user.setUsername(username);
        user.setGender(registerDto.isGender());
        user.setPlaceOfBirth(registerDto.getPlaceOfBirth());
        user.setDateOfBirth(registerDto.getDateOfBirth());
        user.setNationality(registerDto.getNationality());
        user.setType(registerDto.isType());
        user.setPassport(registerDto.getPassport());
        if (registerDto.getCompanyId()!=null) {
            Optional<Company> optionalCompany = companyRepository.findById(registerDto.getCompanyId());
            optionalCompany.ifPresent(user::setCompany);
        }
        //tasodifiy sonni yaratib beradi va userga saqlanadi
        user.setEmailCode(UUID.randomUUID().toString());
        userRepository.save(user);
        //EMAILGA HABAR YUBORISH TASDIQLASH KODINI YUBORADI, METHODINI CHAQIRYABMIZ
        sendEmail(user.getEmail(), user.getEmailCode(),newPassword);
        return new Response("Muafaqiyatli ro'yhatdan o'tdingiz Aakkonutingiz " +
                                    "aktivlashtirishingiz uchun emailni tasdiqlang " +
                                    "va Yangi parol yuborildi", true);
    }
    /**
     * BU METHOD USER EMAILIGA ACTIVE LASHTIRISH CODINI  YUBORISH UCHUN ISHLATILADI
     * @param sendingEmail
     * @param emailCod
     * @return
     */
    public Boolean sendEmail(String sendingEmail,String emailCod,String newPassword) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("Test@pdp.com");
            mailMessage.setTo(sendingEmail);
            mailMessage.setSubject("Akkountni Tasdiqlash");
            mailMessage.setText("<a href='http://localhost:8080/auth/verifyEmail?emailCode="
                    + emailCod + "&email=" + sendingEmail +"&newPassword="+newPassword+"'>Tasdiqlang</a>");
            javaMailSender.send(mailMessage);
            return true;
        }
catch (Exception e){
    return false;
}
    }

    public Boolean sendEmailToTaskDone(String sendingEmail) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("Test@pdp.com");
            mailMessage.setTo(sendingEmail);
            mailMessage.setSubject("Vazifa Bajarildi");
            mailMessage.setText("<a href='http://localhost:8080/auth/taskComplete'>Taskni  Qabul qilish</a>");
            javaMailSender.send(mailMessage);
            return true;
        }
        catch (Exception e){
            return false;
        }

    }

    /**
     * BU METHOD EMAILGA HAT BORGANDAN SO'NG TASDIQLASH HABARINI YUBORGANDA QABUL
     * // QILIB OLIB UNI TEKSHIRIB ACCOUNTINI BAZADA
     * //ACTIVE LASHTIRADI
     *
     * @param email
     * @param emailCode
     * @return
     */
    public Response verifyEmail(String email, String emailCode,String newPassword) {
        Optional<User> optionalUser = userRepository.findByEmailAndEmailCode(email, emailCode);
        if (optionalUser.isPresent()){
            User user= optionalUser.get();
            user.setEnabled(true);
            user.setEmailCode(null);
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            return new Response("Account tasdiqlandi",true);
        }
        return new Response("Akkount alloqachon tasdiqlangan",false);
    }

    /**
     * BU METHOD USERGA LOGIN VA PAROLI BILAN KIRGANDA TOKIN YASAB QAYTARIB JO'NATADI
     * @param loginDto
     * @return
     */
    public Response login(LoginDto loginDto) {
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDto.getUsername(),
                    loginDto.getPassword()));
            User user=(User)authenticate.getPrincipal();
            String token = jwtProvider.generateToken(loginDto.getUsername(), user.getRoles());
            return new Response("Token",true,token);

        }catch (BadCredentialsException badCredentialsException){
            return new Response( "Parol yoki lagin hato",false);
        }
    }

    /**
     * BU METHOD BAZADAN YUZERNI TOPIB QAYTARADI
     * @param email
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> optionalUser= userRepository.findByEmail(email);
        if (optionalUser.isPresent()){
            return optionalUser.get();
        }
        throw new UsernameNotFoundException(" User topilmadi");
    }
    public Boolean emailForTask(String sendingEmail, String text, String subject) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("email@gmail.com");
            mailMessage.setTo(sendingEmail);
            mailMessage.setSubject(subject);
            mailMessage.setText(text);
            javaMailSender.send(mailMessage);
            return true;
        } catch (Exception e) {
            return false;
        }


    }
}
