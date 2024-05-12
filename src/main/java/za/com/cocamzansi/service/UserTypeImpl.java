package za.com.cocamzansi.service;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.com.cocamzansi.entity.UserTypeEntity;
import za.com.cocamzansi.model.ActionStatusType;
import za.com.cocamzansi.model.UserTypeModel;
import za.com.cocamzansi.repository.AddressEntityReposity;
import za.com.cocamzansi.repository.UserRepository;
import za.com.cocamzansi.repository.UserTypeEntityRepository;
import za.com.cocamzansi.service.UserTypeService;

@Service
public class UserTypeImpl implements UserTypeService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserTypeEntityRepository typeEntityRepository;

    @Autowired
    AddressEntityReposity addressEntityReposity;

    @Autowired
    private EntityManager manager;

    public List<UserTypeModel> getUserByType() {
        ActionStatusType actionStatusType = new ActionStatusType(Boolean.valueOf(false), "User.does.not.exists");
        List<UserTypeModel> userTypeModels = new ArrayList<>();
        List<UserTypeEntity> userTypeEntities = this.typeEntityRepository.findAll();
        if (userTypeEntities.size() > 0)
            for (UserTypeEntity userTypeEntity : userTypeEntities) {
                UserTypeModel userTypeModel = new UserTypeModel();
                userTypeModel.setTypeId(userTypeEntity.getTypeId());
                userTypeModel.setTypeName(userTypeEntity.getTypeName());
                userTypeModels.add(userTypeModel);
            }
        return userTypeModels;
    }
}
