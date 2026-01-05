package _bbu.lawfirmapi.services.auth.implement;

import _bbu.lawfirmapi.exceptions.EmailAlreadyExistException;
import _bbu.lawfirmapi.exceptions.NotFoundException;
import _bbu.lawfirmapi.models.DTO.appuser.request.AppUserRequest;
import _bbu.lawfirmapi.models.DTO.appuser.response.AppUserResponse;
import _bbu.lawfirmapi.models.Entity.AppUser;
import _bbu.lawfirmapi.models.Entity.Role;
import _bbu.lawfirmapi.repositories.AppUserRepository;
import _bbu.lawfirmapi.repositories.RoleRepository;
import _bbu.lawfirmapi.services.auth.AppUserService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;
import java.util.List;
@Service
@RequiredArgsConstructor
//@Transactional
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String adminEmail;

    @Override
    public AppUser getCurrentUser(){
        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        System.out.println("AUTH CLASS = " + auth.getClass());
        System.out.println("PRINCIPAL = " + auth.getPrincipal());
        System.out.println("AUTHORITIES = " + auth.getAuthorities());

        return (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
    @Override
    public AppUserResponse getProfile(){
        AppUser appUser = appUserRepository.findById(getCurrentUser().getAppUserId())
                .orElseThrow(() -> new NotFoundException("The profile with id " + getCurrentUser().getAppUserId() + " not found"));
        if (appUser.getRole().getRoleName().equals("ROLE_LAWYER"));
        return appUser.toResponse();
    }
    @Override
    @SneakyThrows
    public String sendNews(String email){

        // prepare mail to user
        MimeMessage mimeMessage =javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

        // set up thymeleaf
        Context context = new Context();

        context.setVariable("subject" , "Test sending mail from Law firm.");
        context.setVariable("user" , email);
        context.setVariable("message" , "Hello from law firm");

        // Process the template

        String htmlContent = templateEngine.process("notification-template", context);
        mimeMessageHelper.setSubject("New Announcement from GCLAW group");
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setFrom(adminEmail);
        mimeMessageHelper.setText(htmlContent, true);
        javaMailSender.send(mimeMessage);

        return "This new announcement have been sending to " + email;
    }



}
