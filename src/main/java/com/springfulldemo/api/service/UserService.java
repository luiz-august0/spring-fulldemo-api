package com.springfulldemo.api.service;

import com.springfulldemo.api.external.s3.S3StorageService;
import com.springfulldemo.api.model.entities.User;
import com.springfulldemo.api.model.enums.EnumUserRole;
import com.springfulldemo.api.repository.UserRepository;
import com.springfulldemo.api.utils.FileUtil;
import com.springfulldemo.api.utils.StringUtil;
import com.springfulldemo.api.utils.Utils;
import com.springfulldemo.api.validators.MultipartBeanValidator;
import com.springfulldemo.api.validators.UserValidator;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService extends AbstractService<UserRepository, User, UserValidator> {
    private final UserRepository userRepository;

    private final UserValidator validator;

    private final S3StorageService s3StorageService;

    private final MultipartBeanValidator multipartBeanValidator;

    public UserService(UserRepository userRepository, S3StorageService s3StorageService) {
        super(userRepository, new User(), new UserValidator(userRepository));
        this.userRepository = userRepository;
        this.validator = new UserValidator(userRepository);
        this.s3StorageService = s3StorageService;
        this.multipartBeanValidator = new MultipartBeanValidator();
    }

    @Override
    @Transactional
    public User insert(User user) {
        user.setRole(EnumUserRole.ADMIN);

        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

        resolverUserPhoto(user, null);

        validator.validate(user);

        userRepository.save(user);

        return user;
    }

    @Override
    @Transactional
    public User update(Integer id, User user) {
        User userOld = super.findAndValidate(id);

        user.setId(userOld.getId());
        user.setRole(userOld.getRole());
        user.setActive(Utils.nvl(user.getActive(), Boolean.TRUE));

        if (userOld.getLogin().equals("admin")) {
            user.setLogin("admin");
        }

        if (StringUtil.isNotNullOrEmpty(user.getPassword())) {
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        } else {
            user.setPassword(userOld.getPassword());
        }

        resolverUserPhoto(user, userOld.getPhoto());

        validator.validate(user);

        userRepository.save(user);

        return user;
    }

    @Transactional
    public User updateContextUser(User user) {
        user.setActive(Boolean.TRUE);

        return this.update(getUserByContext().getId(), user);
    }

    private void resolverUserPhoto(User user, String oldPhoto) {
        if (StringUtil.isNullOrEmpty(user.getPhoto()) && StringUtil.isNotNullOrEmpty(oldPhoto)) {
            s3StorageService.delete(FileUtil.getFilenameFromS3Url(oldPhoto));

            user.setPhoto(null);
        }

        if (Utils.isNotEmpty(user.getPhotoMultipart())) {
            multipartBeanValidator.validate(user.getPhotoMultipart());

            user.setPhoto(s3StorageService.upload(user.getPhotoMultipart(), true));
        }
    }

}