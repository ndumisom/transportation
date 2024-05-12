package za.com.cocamzansi.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.com.cocamzansi.entity.AddressEntity;
import za.com.cocamzansi.entity.EmailAddressEntity;
import za.com.cocamzansi.entity.TelephoneEntity;
import za.com.cocamzansi.entity.UserEntity;
import za.com.cocamzansi.entity.UserTypeEntity;
import za.com.cocamzansi.model.ActionStatusType;
import za.com.cocamzansi.model.AddressModel;
import za.com.cocamzansi.model.EmailAddressModel;
import za.com.cocamzansi.model.EmailConfirmationModel;
import za.com.cocamzansi.model.LoginModel;
import za.com.cocamzansi.model.TelephoneModel;
import za.com.cocamzansi.model.UserModel;
import za.com.cocamzansi.model.UserTypeModel;
import za.com.cocamzansi.model.geolocationmodels.GeoLocation;
import za.com.cocamzansi.repository.AddressEntityReposity;
import za.com.cocamzansi.repository.EmailAddressRepository;
import za.com.cocamzansi.repository.TelephoneRepository;
import za.com.cocamzansi.repository.UserRepository;
import za.com.cocamzansi.repository.UserTypeEntityRepository;
import za.com.cocamzansi.service.AddressService;
import za.com.cocamzansi.service.EmailConfirmService;
import za.com.cocamzansi.service.LoginService;
import za.com.cocamzansi.service.UserService;
import za.com.cocamzansi.util.validator.Validation;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserTypeEntityRepository typeEntityRepository;

    @Autowired
    AddressEntityReposity addressEntityReposity;

    @Autowired
    EmailAddressRepository emailAddressRepository;

    @Autowired
    TelephoneRepository telephoneRepository;

    @Autowired
    private EntityManager manager;

    @Autowired
    AddressService addressService;

    @Autowired
    LoginService loginService;

    @Autowired
    EmailConfirmService emailConfirmService;

    @Autowired
    SmsService smsService;

    public ActionStatusType createUser(UserModel userModel) throws IOException, MessagingException {
        if (userModel == null)
            return new ActionStatusType(Boolean.valueOf(false), "cannot.save.null.object");
        List<EmailAddressEntity> emailAddressEntities = this.emailAddressRepository.findAll();
        for (EmailAddressEntity emailAddressEntity1 : emailAddressEntities) {
            if (emailAddressEntity1.getEmailAddress().trim().equalsIgnoreCase(userModel.getEmailAddressModel().getEmailAddress().trim()))
                return new ActionStatusType(Boolean.valueOf(false), "Email.address.already.exists");
        }
        List<UserTypeEntity> userTypeEntities = this.typeEntityRepository.findAll();
        UserTypeModel userTypeModel = new UserTypeModel();
        for (UserTypeEntity userTypeEntity : userTypeEntities) {
            if (userTypeEntity.getTypeName().trim().equalsIgnoreCase(userModel.getTypeModel().getTypeName().trim())) {
                userTypeModel.setTypeId(userTypeEntity.getTypeId());
                userTypeModel.setTypeName(userTypeEntity.getTypeName());
                userModel.setTypeModel(userTypeModel);
            }
        }
        if (!Validation.isValidEmailAddress(userModel.getEmailAddressModel().getEmailAddress()))
            return new ActionStatusType(Boolean.valueOf(false), "Invalid.email.address");
        List<TelephoneEntity> telephoneEntities = this.telephoneRepository.findAll();
        for (TelephoneEntity telephoneEntity1 : telephoneEntities) {
            if (telephoneEntity1.getTelephoneNumber().trim().equalsIgnoreCase(userModel.getTelephoneModel().getTelephoneNumber().trim()))
                return new ActionStatusType(Boolean.valueOf(false), "Phone.number.already.exists");
        }
        if (!Validation.isValidPhoneNumber(userModel.getTelephoneModel().getTelephoneNumber()))
            return new ActionStatusType(Boolean.valueOf(false), "Invalid.phone.number");
        AddressModel addressModel = new AddressModel();
        if (userModel.getAddressModel().getStreetNumber() != null && !userModel.getAddressModel().getStreetNumber().equals(Integer.valueOf(0))) {
            String streetNumber = userModel.getAddressModel().getStreetNumber().toString();
            String streetNumberNumeric = streetNumber.replaceAll("[^0-9.]", "");
            Integer streetNum = Integer.valueOf(Integer.parseInt(streetNumberNumeric));
            addressModel.setStreetNumber(streetNum);
        } else {
            addressModel.setStreetNumber(Integer.valueOf(9));
        }
        if (userModel.getAddressModel().getStreetName() != null && !userModel.getAddressModel().getStreetName().isEmpty()) {
            addressModel.setStreetName(userModel.getAddressModel().getStreetName());
        } else {
            addressModel.setStreetName("Caledone Street");
        }
        if (userModel.getAddressModel().getSuburb() != null && !userModel.getAddressModel().getSuburb().isEmpty()) {
            addressModel.setSuburb(userModel.getAddressModel().getSuburb());
        } else {
            addressModel.setSuburb("Goodwood");
        }
        if (userModel.getAddressModel().getCity() != null && !userModel.getAddressModel().getCity().isEmpty())
            addressModel.setCity(userModel.getAddressModel().getCity());
        if (userModel.getAddressModel().getPostalcode() != null && !userModel.getAddressModel().getPostalcode().equals(Integer.valueOf(0))) {
            addressModel.setPostalcode(userModel.getAddressModel().getPostalcode());
        } else {
            addressModel.setPostalcode(Integer.valueOf(7640));
        }
        GeoLocation geoLocation = this.addressService.getGeolocation(addressModel.getStreetNumber() + " " + addressModel.getStreetName() + " " + addressModel.getSuburb() + " " + addressModel.getCity());
        String fullAddress = geoLocation.getOrigin_addresses().get(0);
        addressModel.setFullAddress(fullAddress);
        AddressModel addressModel1 = createUserAddress(addressModel);
        UserEntity userEntity = new UserEntity();
        UserTypeEntity typeEntity = new UserTypeEntity();
        AddressEntity addressEntity = new AddressEntity();
        typeEntity.setTypeId(userModel.getTypeModel().getTypeId());
        typeEntity.setTypeName(userModel.getTypeModel().getTypeName());
        if (addressModel1.getAddressId() != null) {
            addressEntity.setAddressId(addressModel1.getAddressId());
        } else {
            addressEntity.setAddressId(userModel.getAddressModel().getAddressId());
        }
        addressEntity.setPostalcode(userModel.getAddressModel().getPostalcode());
        addressEntity.setStreetName(userModel.getAddressModel().getStreetName());
        addressEntity.setStreetNumber(userModel.getAddressModel().getStreetNumber());
        addressEntity.setSuburb(userModel.getAddressModel().getSuburb());
        addressEntity.setCity(userModel.getAddressModel().getCity());
        EmailAddressModel emailAddressModel = new EmailAddressModel();
        emailAddressModel.setEmailAddress(userModel.getEmailAddressModel().getEmailAddress());
        EmailAddressEntity emailAddressEntity = new EmailAddressEntity();
        if (userModel.getEmailAddressModel().getEmailAddress() != null) {
            emailAddressEntity.setEmailId(userModel.getEmailAddressModel().getEmailId());
        } else {
            emailAddressEntity.setEmailId(userModel.getEmailAddressModel().getEmailId());
        }
        EmailConfirmationModel emailConfirmationModel = new EmailConfirmationModel();
        if (userModel.getEmailAddressModel().getEmailAddress() != null) {
            emailConfirmationModel.setEmail(userModel.getEmailAddressModel().getEmailAddress());
        } else {
            emailConfirmationModel.setEmail(userModel.getEmailAddressModel().getEmailAddress());
        }
        TelephoneModel telephoneModel = new TelephoneModel();
        telephoneModel.setTelephoneNumber(userModel.getTelephoneModel().getTelephoneNumber());
        TelephoneEntity telephoneEntity = new TelephoneEntity();
        if (userModel.getTelephoneModel().getTelephoneNumber() != null) {
            telephoneEntity.setTelephoneId(userModel.getTelephoneModel().getTelephoneId());
        } else {
            telephoneEntity.setTelephoneId(userModel.getTelephoneModel().getTelephoneId());
        }
        userEntity.setEmailAddressEntity(emailAddressEntity);
        userEntity.setTelephoneId(telephoneEntity);
        userEntity.setFirstName(userModel.getFirstName());
        userEntity.setLastName(userModel.getLastName());
        if (userModel.getUserProfilePicture() != null && !userModel.getUserProfilePicture().isEmpty() && !userModel.getUserProfilePicture().trim().equalsIgnoreCase("")) {
            userEntity.setUserProfilePicture(userModel.getUserProfilePicture());
        } else {
            userEntity.setUserProfilePicture("http://localhost/dreams/cocamzansi/images/default-avatar.jpg");
        }
        userEntity.setAddressId(addressEntity);
        userEntity.setTypeEntityId(typeEntity);
        if (userEntity == null)
            return new ActionStatusType(Boolean.valueOf(false), "cannot.save.null.object");
        UserEntity newCreatedEntity = null;
        if (this.userRepository.findByFirstNameAndLastName(userModel.getFirstName(), userModel.getLastName()).size() < 1) {
            if (userModel.getAddressModel().getAddressId() == null) {
                List<AddressEntity> addressList = this.addressEntityReposity.checkAddressExists(addressModel.getStreetNumber(), addressModel.getStreetName(), addressModel.getSuburb(), addressModel.getPostalcode());
                AddressEntity existingAddress = addressList.get(addressList.size() - 1);
                userEntity.setAddressId(existingAddress);
                EmailAddressModel emailAddressModel1 = createUserEmailAddress(emailAddressModel);
                TelephoneModel telephoneModel1 = createUserPhone(telephoneModel);
                TelephoneEntity telephoneEntity1 = new TelephoneEntity();
                telephoneEntity1.setTelephoneNumber(telephoneModel1.getTelephoneNumber());
                telephoneEntity1.setTelephoneId(telephoneModel1.getTelephoneId());
                EmailAddressEntity emailAddressEntity1 = new EmailAddressEntity();
                emailAddressEntity1.setEmailId(emailAddressModel1.getEmailId());
                emailAddressEntity1.setEmailAddress(emailAddressModel1.getEmailAddress());
                userEntity.setEmailAddressEntity(emailAddressEntity1);
                userEntity.setTelephoneId(telephoneEntity1);
                newCreatedEntity = (UserEntity)this.userRepository.saveAndFlush(userEntity);
            } else {
                EmailAddressModel emailAddressModel1 = createUserEmailAddress(emailAddressModel);
                TelephoneModel telephoneModel1 = createUserPhone(telephoneModel);
                TelephoneEntity telephoneEntity1 = new TelephoneEntity();
                telephoneEntity1.setTelephoneNumber(telephoneModel1.getTelephoneNumber());
                telephoneEntity1.setTelephoneId(telephoneModel1.getTelephoneId());
                EmailAddressEntity emailAddressEntity1 = new EmailAddressEntity();
                emailAddressEntity1.setEmailId(emailAddressModel1.getEmailId());
                emailAddressEntity1.setEmailAddress(emailAddressModel1.getEmailAddress());
                userEntity.setEmailAddressEntity(emailAddressEntity1);
                userEntity.setTelephoneId(telephoneEntity1);
                newCreatedEntity = (UserEntity)this.userRepository.saveAndFlush(userEntity);
            }
            LoginModel loginModel1 = new LoginModel();
            UserModel userModel2 = new UserModel();
            userModel2.setUserId(newCreatedEntity.getUserId());
            loginModel1.setUserModel(userModel2);
            loginModel1.setUsername(userModel.getLoginModel().getUsername());
            loginModel1.setPassword(userModel.getLoginModel().getPassword());
            this.loginService.createLoginCredentials(loginModel1);
            this.emailConfirmService.createEmailConfirmation(emailConfirmationModel);
//            this.smsService.sendSMSByRequestId("");
            return new ActionStatusType(Boolean.valueOf(true), "User.created.successful");
        }
        LoginModel loginModel = new LoginModel();
        UserModel userModel1 = new UserModel();
        List<UserEntity> userEntities = this.userRepository.findAll();
        for (UserEntity userEntit : userEntities) {
            if (userEntit.getFirstName().trim().equalsIgnoreCase(userModel.getFirstName()) && userEntit
                    .getLastName().trim().equalsIgnoreCase(userModel.getLastName())) {
                userModel1.setUserId(userEntit.getUserId());
                loginModel.setUserModel(userModel1);
                loginModel.setUsername(userModel.getLoginModel().getUsername());
                loginModel.setPassword(userModel.getLoginModel().getPassword());
            }
        }
        this.loginService.createLoginCredentials(loginModel);
        return new ActionStatusType(Boolean.valueOf(false), "User.already.exists");
    }

    public UserModel getUser(Integer userId) {
        ActionStatusType actionStatusType = new ActionStatusType(Boolean.valueOf(false), "User.does.not.exists");
        if (this.userRepository.findById(userId).isPresent()) {
            Optional<UserEntity> userEntity = this.userRepository.findById(userId);
            AddressModel addressModel = new AddressModel();
            addressModel.setAddressId(((UserEntity)userEntity.get()).getAddressId().getAddressId());
            addressModel.setPostalcode(((UserEntity)userEntity.get()).getAddressId().getPostalcode());
            addressModel.setStreetNumber(((UserEntity)userEntity.get()).getAddressId().getStreetNumber());
            addressModel.setStreetName(((UserEntity)userEntity.get()).getAddressId().getStreetName());
            addressModel.setSuburb(((UserEntity)userEntity.get()).getAddressId().getSuburb());
            addressModel.setCity(((UserEntity)userEntity.get()).getAddressId().getCity());
            UserTypeModel typeModel = new UserTypeModel();
            typeModel.setTypeId(((UserEntity)userEntity.get()).getTypeEntityId().getTypeId());
            typeModel.setTypeName(((UserEntity)userEntity.get()).getTypeEntityId().getTypeName());
            UserModel userModel = new UserModel();
            userModel.setUserId(((UserEntity)userEntity.get()).getUserId());
            userModel.setFirstName(((UserEntity)userEntity.get()).getFirstName());
            userModel.setLastName(((UserEntity)userEntity.get()).getLastName());
            userModel.setUserProfilePicture(((UserEntity)userEntity.get()).getUserProfilePicture());
            userModel.setAddressModel(addressModel);
            userModel.setTypeModel(typeModel);
            TelephoneModel telephoneModel = new TelephoneModel();
            telephoneModel.setTelephoneNumber(((UserEntity)userEntity.get()).getTelephoneId().getTelephoneNumber());
            userModel.setTelephoneModel(telephoneModel);
            return userModel;
        }
        return new UserModel(actionStatusType);
    }

    public ActionStatusType delete(Integer userId) {
        if (this.userRepository.findById(userId).isPresent()) {
            UserEntity userEntity = (UserEntity)this.userRepository.getOne(userId);
            this.userRepository.delete(userEntity);
            return new ActionStatusType(Boolean.valueOf(true), "Deleted.successful");
        }
        return new ActionStatusType(Boolean.valueOf(false), "User.not.found");
    }

    public List<UserModel> getUsers() {
        ActionStatusType actionStatusType = new ActionStatusType(Boolean.valueOf(false), "User.does.not.exists");
        List<UserModel> userModels = new ArrayList<>();
        List<UserEntity> userEntityList = this.userRepository.findAll();
        for (UserEntity userEntity : userEntityList) {
            AddressModel addressModel = new AddressModel();
            addressModel.setAddressId(userEntity.getAddressId().getAddressId());
            addressModel.setPostalcode(userEntity.getAddressId().getPostalcode());
            addressModel.setStreetNumber(userEntity.getAddressId().getStreetNumber());
            addressModel.setStreetName(userEntity.getAddressId().getStreetName());
            addressModel.setSuburb(userEntity.getAddressId().getSuburb());
            addressModel.setCity(userEntity.getAddressId().getCity());
            addressModel.setFullAddress(userEntity.getAddressId().getFullAddress());
            UserTypeModel typeModel = new UserTypeModel();
            typeModel.setTypeId(userEntity.getTypeEntityId().getTypeId());
            typeModel.setTypeName(userEntity.getTypeEntityId().getTypeName());
            UserModel userModel = new UserModel();
            userModel.setUserId(userEntity.getUserId());
            userModel.setFirstName(userEntity.getFirstName());
            userModel.setLastName(userEntity.getLastName());
            userModel.setAddressModel(addressModel);
            userModel.setTypeModel(typeModel);
            userModel.setCreated(userEntity.getCreated());
            userModels.add(userModel);
        }
        return userModels;
    }

    public List<UserModel> getUserByType(Integer userTypeId) {
        ActionStatusType actionStatusType = new ActionStatusType(Boolean.valueOf(false), "User.does.not.exists");
        List<UserModel> userModels = new ArrayList<>();
        UserTypeEntity typeEntity = this.typeEntityRepository.findById(userTypeId).get();
        List<UserEntity> userEntityList = this.userRepository.findAllByUserType(typeEntity);
        if (userEntityList.size() < 1) {
            UserModel userModel = new UserModel(actionStatusType);
            userModels.add(userModel);
            return userModels;
        }
        for (UserEntity userEntity : userEntityList) {
            AddressModel addressModel = new AddressModel();
            addressModel.setAddressId(userEntity.getAddressId().getAddressId());
            addressModel.setPostalcode(userEntity.getAddressId().getPostalcode());
            addressModel.setStreetNumber(userEntity.getAddressId().getStreetNumber());
            addressModel.setStreetName(userEntity.getAddressId().getStreetName());
            addressModel.setSuburb(userEntity.getAddressId().getSuburb());
            addressModel.setCity(userEntity.getAddressId().getCity());
            UserTypeModel typeModel = new UserTypeModel();
            typeModel.setTypeId(userEntity.getTypeEntityId().getTypeId());
            typeModel.setTypeName(userEntity.getTypeEntityId().getTypeName());
            UserModel userModel = new UserModel();
            userModel.setUserId(userEntity.getUserId());
            userModel.setFirstName(userEntity.getFirstName());
            userModel.setLastName(userEntity.getLastName());
            userModel.setAddressModel(addressModel);
            userModel.setTypeModel(typeModel);
            userModels.add(userModel);
        }
        return userModels;
    }

    public List<UserModel> getUserByAddress(Integer userTypeId) {
        ActionStatusType actionStatusType = new ActionStatusType(Boolean.valueOf(false), "User.does.not.exists");
        List<UserModel> userModels = new ArrayList<>();
        AddressEntity addressEntity = this.addressEntityReposity.findById(userTypeId).get();
        List<UserEntity> userEntityList = this.userRepository.findAllByUserAddress(addressEntity);
        if (userEntityList.size() < 1) {
            UserModel userModel = new UserModel(actionStatusType);
            userModels.add(userModel);
            return userModels;
        }
        for (UserEntity userEntity : userEntityList) {
            AddressModel addressModel = new AddressModel();
            addressModel.setAddressId(userEntity.getAddressId().getAddressId());
            addressModel.setPostalcode(userEntity.getAddressId().getPostalcode());
            addressModel.setStreetNumber(userEntity.getAddressId().getStreetNumber());
            addressModel.setStreetName(userEntity.getAddressId().getStreetName());
            addressModel.setSuburb(userEntity.getAddressId().getSuburb());
            addressModel.setCity(userEntity.getAddressId().getCity());
            UserTypeModel typeModel = new UserTypeModel();
            typeModel.setTypeId(userEntity.getTypeEntityId().getTypeId());
            typeModel.setTypeName(userEntity.getTypeEntityId().getTypeName());
            UserModel userModel = new UserModel();
            userModel.setUserId(userEntity.getUserId());
            userModel.setFirstName(userEntity.getFirstName());
            userModel.setLastName(userEntity.getLastName());
            userModel.setAddressModel(addressModel);
            userModel.setTypeModel(typeModel);
            userModels.add(userModel);
        }
        return userModels;
    }

    private AddressModel createUserAddress(AddressModel addressModel) {
        if (addressModel == null)
            return new AddressModel();
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setPostalcode(addressModel.getPostalcode());
        addressEntity.setStreetName(addressModel.getStreetName());
        addressEntity.setStreetNumber(addressModel.getStreetNumber());
        addressEntity.setSuburb(addressModel.getSuburb());
        addressEntity.setCity(addressModel.getCity());
        addressEntity.setFullAddress(addressModel.getFullAddress());
        if (addressEntity == null)
            return new AddressModel();
        if (this.addressEntityReposity.checkAddressExists(addressModel.getStreetNumber(), addressModel.getStreetName(), addressModel.getSuburb(), addressModel.getPostalcode()).size() < 1) {
            AddressEntity newCreatedEntity = (AddressEntity)this.addressEntityReposity.saveAndFlush(addressEntity);
            AddressModel addressModel1 = new AddressModel();
            addressModel1.setAddressId(newCreatedEntity.getAddressId());
            addressModel1.setPostalcode(newCreatedEntity.getPostalcode());
            addressModel1.setStreetName(newCreatedEntity.getStreetName());
            addressModel1.setStreetNumber(newCreatedEntity.getStreetNumber());
            addressModel1.setSuburb(newCreatedEntity.getSuburb());
            addressModel1.setCity(newCreatedEntity.getCity());
            addressModel1.setFullAddress(newCreatedEntity.getFullAddress());
            return addressModel1;
        }
        AddressModel emailAddressModel1 = new AddressModel();
        List<AddressEntity> addressEntities = this.addressEntityReposity.findAll();
        for (AddressEntity emailAddressEntity1 : addressEntities) {
            if (emailAddressEntity1.getPostalcode().equals(addressModel.getPostalcode()) && emailAddressEntity1.getStreetName().trim().equalsIgnoreCase(addressModel.getStreetName()) && emailAddressEntity1.getStreetNumber().equals(addressModel.getStreetNumber()) && emailAddressEntity1.getSuburb().trim().equalsIgnoreCase(addressModel.getSuburb()))
                emailAddressModel1.setAddressId(emailAddressEntity1.getAddressId());
        }
        return emailAddressModel1;
    }

    private EmailAddressModel createUserEmailAddress(EmailAddressModel emailAddressModel) {
        if (emailAddressModel == null)
            return new EmailAddressModel();
        Validation validation = new Validation();
        EmailAddressEntity emailAddressEntity = new EmailAddressEntity();
        emailAddressEntity.setEmailAddress(emailAddressModel.getEmailAddress());
        if (emailAddressEntity.getEmailAddress() == null)
            return new EmailAddressModel();
        if (this.emailAddressRepository.checkEmailAddressExists(emailAddressModel.getEmailAddress()).size() < 1) {
            EmailAddressEntity newCreatedEmailAddressEntity = (EmailAddressEntity)this.emailAddressRepository.saveAndFlush(emailAddressEntity);
            EmailAddressModel emailAddressModel2 = new EmailAddressModel();
            emailAddressModel2.setEmailAddress(newCreatedEmailAddressEntity.getEmailAddress());
            emailAddressModel2.setEmailId(newCreatedEmailAddressEntity.getEmailId());
            return emailAddressModel2;
        }
        EmailAddressModel emailAddressModel1 = new EmailAddressModel();
        List<EmailAddressEntity> emailAddressEntities = this.emailAddressRepository.findAll();
        for (EmailAddressEntity emailAddressEntity1 : emailAddressEntities) {
            if (emailAddressEntity1.getEmailAddress().trim().equalsIgnoreCase(emailAddressModel.getEmailAddress().trim())) {
                emailAddressModel1.setEmailId(emailAddressEntity1.getEmailId());
                emailAddressModel1.setEmailAddress(emailAddressEntity1.getEmailAddress());
            }
        }
        return emailAddressModel1;
    }

    private TelephoneModel createUserPhone(TelephoneModel telephoneModel) {
        if (telephoneModel == null)
            return new TelephoneModel();
        TelephoneEntity telephoneEntity = new TelephoneEntity();
        telephoneEntity.setTelephoneNumber(telephoneModel.getTelephoneNumber());
        if (telephoneEntity == null)
            return new TelephoneModel();
        if (this.telephoneRepository.checkTelephoneExists(telephoneModel.getTelephoneNumber()).size() < 1) {
            TelephoneEntity newCreatedTelephoneEntity = (TelephoneEntity)this.telephoneRepository.saveAndFlush(telephoneEntity);
            TelephoneModel telephoneModel2 = new TelephoneModel();
            telephoneModel2.setTelephoneNumber(newCreatedTelephoneEntity.getTelephoneNumber());
            telephoneModel2.setTelephoneId(newCreatedTelephoneEntity.getTelephoneId());
            return telephoneModel2;
        }
        TelephoneModel telephoneModel1 = new TelephoneModel();
        List<TelephoneEntity> telephoneEntities = this.telephoneRepository.findAll();
        for (TelephoneEntity telephoneEntity1 : telephoneEntities) {
            if (telephoneEntity1.getTelephoneNumber().trim().equalsIgnoreCase(telephoneModel.getTelephoneNumber().trim())) {
                telephoneModel1.setTelephoneId(telephoneEntity1.getTelephoneId());
                telephoneModel1.setTelephoneNumber(telephoneEntity1.getTelephoneNumber());
            }
        }
        return telephoneModel1;
    }

    public List<UserModel> getUserByFullAddress(String fullAddress) {
        return null;
    }
}
