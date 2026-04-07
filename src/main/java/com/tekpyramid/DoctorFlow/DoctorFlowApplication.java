package com.tekpyramid.DoctorFlow;

import com.google.common.collect.Lists;
import com.tekpyramid.DoctorFlow.Entity.Admin;
import com.tekpyramid.DoctorFlow.Entity.AppUser;
import com.tekpyramid.DoctorFlow.Entity.Role;
import com.tekpyramid.DoctorFlow.Repository.AdminRepository;
import com.tekpyramid.DoctorFlow.Repository.AppUserRepository;
import com.tekpyramid.DoctorFlow.Repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@RequiredArgsConstructor
public class DoctorFlowApplication {

    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final AdminRepository adminRepository;
    private final AppUserRepository appUserRepository;

    public static void main(String[] args) {
        SpringApplication.run(DoctorFlowApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {

            // --- Step 1: Create roles ---
            Role userRole = Role.builder().roleName("ROLE_USER").build();
            Role doctorRole = Role.builder().roleName("ROLE_DOCTOR").build();
            Role adminRole = Role.builder().roleName("ROLE_ADMIN").appUsers(Lists.newArrayList()).build();

            // --- Step 2: Create admin entity ---
            Admin admin01 = Admin.builder()
                    .adminId("ADMIN01")
                    .adminName("admin01")
                    .build();

            // --- Step 3: Create AppUser credentials for admin ---
            AppUser adminCredentials = AppUser.builder()
                    .username(admin01.getAdminName()) // ✅ "admin01"
                    .password(passwordEncoder.encode("qwerty"))
                    .roles(Lists.newArrayList())
                    .build();


            // --- Step 4: Save roles and entities ---
            if (roleRepository.count() == 0) {

                roleRepository.save(userRole);
                roleRepository.save(doctorRole);

                adminRepository.save(admin01);

                // create and save roles
            }


            // --- Step 5: Link admin credentials to role ---
            adminCredentials.getRoles().add(adminRole);
            adminRole.getAppUsers().add(adminCredentials);

            // --- Step 6: Save admin role and credentials ---
            roleRepository.save(adminRole);
            appUserRepository.save(adminCredentials);

            System.out.println("✅ Roles and Admin initialized successfully!");
        };
    }
}
