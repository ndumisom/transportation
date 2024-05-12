package za.com.cocamzansi.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.com.cocamzansi.entity.RequestEntity;
import za.com.cocamzansi.model.RequestModel;
import za.com.cocamzansi.repository.ReloadRepository;
import za.com.cocamzansi.service.ReloadService;

@Service
public class ReloadServiceImpl implements ReloadService {
    @Autowired
    ReloadRepository reloadRepository;

    public List<RequestModel> listOfRequests() {
        List<RequestModel> requestModels = new ArrayList<>();
        List<RequestEntity> requestEntityList = this.reloadRepository.findAll();
        for (RequestEntity requestEntity : requestEntityList) {
            RequestModel requestModel = new RequestModel();
            requestModel.setStatus(requestEntity.getStatus());
            requestModel.setRequestId(requestEntity.getRequestId());
            requestModels.add(requestModel);
        }
        return requestModels;
    }
}