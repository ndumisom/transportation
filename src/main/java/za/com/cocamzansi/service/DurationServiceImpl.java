package za.com.cocamzansi.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.com.cocamzansi.entity.DurationEntity;
import za.com.cocamzansi.entity.RequestAuditEntity;
import za.com.cocamzansi.entity.UserEntity;
import za.com.cocamzansi.model.DurationModel;
import za.com.cocamzansi.model.RequestModel;
import za.com.cocamzansi.repository.DurationRepository;
import za.com.cocamzansi.repository.RequestAuditRepository;
import za.com.cocamzansi.repository.RequestRepository;
import za.com.cocamzansi.repository.UserRepository;
import za.com.cocamzansi.service.DurationService;

@Service
public class DurationServiceImpl implements DurationService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    DurationRepository durationRepository;

    @Autowired
    RequestRepository requestRepository;

    @Autowired
    RequestAuditRepository requestAuditRepository;

    public DurationModel findByRequestIdAndUserId(Integer userId, Integer requestId) {
        Optional<UserEntity> userEntities = this.userRepository.findById(userId);
        List<RequestAuditEntity> requestAuditEntities = this.requestAuditRepository.getRequestByRequestId(requestId);
        DurationModel durationModel = new DurationModel();
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(((UserEntity)userEntities.get()).getUserId());
        List<DurationEntity> durationEntities = null;
        for (RequestAuditEntity requestAuditEntity : requestAuditEntities)
            durationEntities = this.durationRepository.findByRequestAuditIdAndUserId(requestAuditEntity, userEntities.get());
        for (DurationEntity durationEntit : durationEntities) {
            if (durationEntit.getCreated() != null)
                durationModel.setCreated(durationEntit.getCreated());
            if (durationEntit.getUpdated() != null)
                durationModel.setUpdated(durationEntit.getUpdated());
            if (durationEntit.getDurationId() != null)
                durationModel.setDurationId(durationEntit.getDurationId());
            if (durationEntit.getStatus() != null)
                durationModel.setStatus(durationEntit.getStatus());
            RequestModel requestModel = new RequestModel();
            if (((RequestAuditEntity)requestAuditEntities.get(0)).getRequestId() != null)
                requestModel.setRequestId(((RequestAuditEntity)requestAuditEntities.get(0)).getRequestId());
            if (((RequestAuditEntity)requestAuditEntities.get(0)).getUserEntityFromId().getUserId() != null) {
                Optional<UserEntity> userEntiti = this.userRepository.findById(((RequestAuditEntity)requestAuditEntities.get(0)).getUserEntityFromId().getUserId());
                requestModel.setUserFromFirstName(((UserEntity)userEntiti.get()).getFirstName());
            }
            durationModel.setRequestId(requestModel);
            durationModel.setCalculateTimeDifference(calculateTimeDifference(durationEntit));
            Map<String, Long> hoursWorked = calculateTimeDifference(durationEntit);
            String getHoursWorked = hoursWorked.values().toString() + hoursWorked.keySet().toString();
            durationModel.setHoursWorked(getHoursWorked);
            durationModel.setServiceType(durationEntit.getRequestAuditId().getRequestMessage());
        }
        return durationModel;
    }

    public List<DurationModel> findRequestByUserId(Integer userId) {
        List<DurationModel> durationModels = new ArrayList<>();
        UserEntity userEntity = this.userRepository.findByUserId(userId);
        List<DurationEntity> durationEntities = this.durationRepository.findByUserIdOrderByDurationIdDesc(userEntity);
        List<RequestAuditEntity> requestAuditEntities = this.requestAuditRepository.findByRequestToOrderByRequestToDesc(userId.toString());
        for (DurationEntity durationEntity : durationEntities) {
            DurationModel durationModel = new DurationModel();
            if (durationEntity.getCreated() != null)
                durationModel.setCreated(durationEntity.getCreated());
            if (durationEntity.getUpdated() != null)
                durationModel.setUpdated(durationEntity.getUpdated());
            if (durationEntity.getDurationId() != null)
                durationModel.setDurationId(durationEntity.getDurationId());
            if (durationEntity.getStatus() != null)
                durationModel.setStatus(durationEntity.getStatus());
            RequestModel requestModel = new RequestModel();
            for (RequestAuditEntity requestAuditEntity : requestAuditEntities) {
                if (requestAuditEntity.getRequestId() != null)
                    requestModel.setRequestId(requestAuditEntity.getRequestId());
                if (requestAuditEntity.getUserEntityFromId().getUserId() != null) {
                    Optional<UserEntity> userEntiti = this.userRepository.findById(requestAuditEntity.getUserEntityFromId().getUserId());
                    requestModel.setUserFromFirstName(((UserEntity)userEntiti.get()).getFirstName());
                }
                durationModel.setServiceType(requestAuditEntity.getRequestMessage());
            }
            durationModel.setRequestId(requestModel);
            durationModel.setCalculateTimeDifference(calculateTimeDifference(durationEntity));
            Map<String, Long> hoursWorked = calculateTimeDifference(durationEntity);
            String getHoursWorked = hoursWorked.values().toString() + hoursWorked.keySet().toString();
            durationModel.setHoursWorked(getHoursWorked);
            durationModels.add(durationModel);
        }
        return durationModels;
    }

    Map<String, Long> calculateTimeDifference(DurationEntity durationEntity) {
        Map<String, Long> duration = new HashMap<>();
        if (durationEntity.getUpdated() != null) {
            long diff = durationEntity.getUpdated().getTime() - durationEntity.getCreated().getTime();
            long diffSeconds = diff / 1000L % 60L;
            long diffMinutes = diff / 60000L % 60L;
            long diffHours = diff / 3600000L % 24L;
            long diffDays = diff / 86400000L;
            System.out.print(diffDays + " days, ");
            System.out.print(diffHours + " hours, ");
            System.out.print(diffMinutes + " minutes, ");
            System.out.print(diffSeconds + " seconds.");
            if (diffDays > 0L) {
                duration.put("days", Long.valueOf(diffDays));
            } else if (diffHours > 0L) {
                duration.put("hours", Long.valueOf(diffHours));
            } else if (diffMinutes > 0L) {
                duration.put("minutes", Long.valueOf(diffMinutes));
            } else if (diffSeconds > 0L) {
                duration.put("seconds", Long.valueOf(diffSeconds));
            }
        }
        return duration;
    }
}