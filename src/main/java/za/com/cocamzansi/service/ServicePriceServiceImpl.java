package za.com.cocamzansi.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.com.cocamzansi.entity.ServiceCategoryEntity;
import za.com.cocamzansi.entity.ServiceEntity;
import za.com.cocamzansi.model.ServiceCategoryModel;
import za.com.cocamzansi.model.ServiceModel;
import za.com.cocamzansi.repository.ServiceCategoryRepository;
import za.com.cocamzansi.repository.ServiceRepository;
import za.com.cocamzansi.service.ServicePriceService;

@Service
public class ServicePriceServiceImpl implements ServicePriceService {
    @Autowired
    ServiceRepository serviceRepository;

    @Autowired
    ServiceCategoryRepository serviceCategoryRepository;

    public List<ServiceModel> listOfServices() {
        List<ServiceEntity> serviceEntities = this.serviceRepository.findServices();
        List<ServiceModel> serviceModels = new ArrayList<>();
        for (ServiceEntity serviceEntity : serviceEntities) {
            ServiceModel serviceModel = new ServiceModel();
            serviceModel.setCreateDateTime(serviceEntity.getCreateDateTime());
            serviceModel.setServiceId(serviceEntity.getServiceId());
            serviceModel.setServiceName(serviceEntity.getServiceName());
            serviceModel.setServicePrice(serviceEntity.getServicePrice());
            ServiceCategoryEntity serviceCategoryEntity = (ServiceCategoryEntity)this.serviceCategoryRepository.getOne(serviceEntity.getServiceCategoryEntity().getServiceCategoryId());
            ServiceCategoryModel serviceCategoryModel = new ServiceCategoryModel();
            serviceCategoryModel.setServiceCategoryId(serviceCategoryEntity.getServiceCategoryId());
            serviceCategoryModel.setServiceCategoryName(serviceCategoryEntity.getServiceCategoryName());
            serviceModel.setServiceCategoryModel(serviceCategoryModel);
            serviceModels.add(serviceModel);
        }
        return serviceModels;
    }
}
