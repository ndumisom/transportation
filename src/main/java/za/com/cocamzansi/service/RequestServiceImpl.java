package za.com.cocamzansi.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.com.cocamzansi.entity.DurationEntity;
import za.com.cocamzansi.entity.RequestAuditEntity;
import za.com.cocamzansi.entity.RequestEntity;
import za.com.cocamzansi.entity.UserEntity;
import za.com.cocamzansi.model.ActionStatusType;
import za.com.cocamzansi.model.RequestAddressModel;
import za.com.cocamzansi.model.RequestModel;
import za.com.cocamzansi.model.ServiceModel;
import za.com.cocamzansi.model.UserModel;
import za.com.cocamzansi.model.geolocationmodels.CoordinateAddress;
import za.com.cocamzansi.model.geolocationmodels.Coordinates;
import za.com.cocamzansi.model.geolocationmodels.CoordinatesFromAddress;
import za.com.cocamzansi.model.geolocationmodels.Distance;
import za.com.cocamzansi.model.geolocationmodels.DistanceDuration;
import za.com.cocamzansi.model.geolocationmodels.Duration;
import za.com.cocamzansi.model.geolocationmodels.GeoLocation;
import za.com.cocamzansi.model.geolocationmodels.RowElementsDistanceDuration;
import za.com.cocamzansi.repository.DurationRepository;
import za.com.cocamzansi.repository.RequestAuditRepository;
import za.com.cocamzansi.repository.RequestRepository;
import za.com.cocamzansi.repository.UserRepository;
import za.com.cocamzansi.service.EmailService;
import za.com.cocamzansi.service.GeolocationService;
import za.com.cocamzansi.service.RequestService;
import za.com.cocamzansi.service.ScheduleRequestService;
import za.com.cocamzansi.service.ScheduleResponseService;
import za.com.cocamzansi.service.ServicePriceService;
import za.com.cocamzansi.service.UserService;
import za.com.cocamzansi.util.localization.MessageByLocaleService;

@Service
public class RequestServiceImpl implements RequestService {
    @Autowired
    RequestRepository requestRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    ServicePriceService servicePriceService;

    @Autowired
    ScheduleRequestService scheduleRequestService;

    @Autowired
    ScheduleResponseService scheduleResponseService;

    @Autowired
    MessageByLocaleService messageByLocaleService;

    @Autowired
    EmailService emailService;

    @Autowired
    HttpServletRequest request;

    @Autowired
    GeolocationService geolocationService;

    @Autowired
    DurationRepository durationRepository;

    @Autowired
    RequestAuditRepository requestAuditRepository;

    @Autowired
    SmsService smsService;

    public ActionStatusType createRequest(RequestModel requestModel) throws IOException, MessagingException {
        CoordinatesFromAddress coordinatesFromAddress;
        if (requestModel == null)
            return new ActionStatusType(Boolean.valueOf(false), "cannot.save.null.object");
        HttpSession checkUserSession = this.request.getSession(false);
        String currentAddress = checkUserSession.getAttribute("currentaddress").toString();
        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setRequestMessage(requestModel.getRequestMessage());
        UserEntity userEntityFrom = new UserEntity();
        UserEntity userEntityTo = new UserEntity();
        userEntityFrom.setUserId(requestModel.getUserModelFromId().getUserId());
        userEntityTo.setUserId(requestModel.getUserModelToId().getUserId());
        requestEntity.setUserEntityFromId(userEntityFrom);
        if (requestModel.getUserModelFromId().getAddressModel().getFullAddress() != null && !requestModel.getUserModelFromId().getAddressModel().getFullAddress().trim().equalsIgnoreCase("")) {
            requestEntity.setFullAddress(requestModel.getUserModelFromId().getAddressModel().getFullAddress());
            coordinatesFromAddress = this.geolocationService.coordinatesByAddress(requestModel.getUserModelFromId().getAddressModel().getFullAddress());
        } else {
            requestEntity.setFullAddress(currentAddress);
            coordinatesFromAddress = this.geolocationService.coordinatesByAddress(currentAddress);
        }
        if (coordinatesFromAddress != null) {
            requestEntity.setLatitute(((CoordinateAddress)coordinatesFromAddress.getResults().get(0)).getGeometry().getLocation().getLat());
            requestEntity.setLongitute(((CoordinateAddress)coordinatesFromAddress.getResults().get(0)).getGeometry().getLocation().getLng());
        }
        this.requestRepository.saveAndFlush(requestEntity);
        requestModel.setUserToPhoneNumber("27731230109");
        this.smsService.sendSMSByRequestId(requestModel);

        updateRequestAudit(requestEntity);
        return new ActionStatusType(Boolean.valueOf(true), "success");
    }

    public void updateRequestAudit(RequestEntity requestEntity) {
        List<RequestAuditEntity> requestAuditEntities = null;
        if (requestEntity.getRequestId() != null)
            requestAuditEntities = this.requestAuditRepository.getRequestByRequestId(requestEntity.getRequestId());
        if (requestAuditEntities != null && requestAuditEntities.size() > 0) {
            for (RequestAuditEntity requestAuditEntit : requestAuditEntities) {
                if (requestEntity.getCreateDateTime() != null)
                    requestAuditEntit.setCreateDateTime(requestEntity.getCreateDateTime());
                if (requestEntity.getFullAddress() != null)
                    requestAuditEntit.setFullAddress(requestEntity.getFullAddress());
                if (requestEntity.getLatitute() != null)
                    requestAuditEntit.setLatitute(requestEntity.getLatitute());
                if (requestEntity.getLongitute() != null)
                    requestAuditEntit.setLongitute(requestEntity.getLongitute());
                if (requestEntity.getRequestId() != null)
                    requestAuditEntit.setRequestId(requestEntity.getRequestId());
                if (requestEntity.getRequestTo() != null)
                    requestAuditEntit.setRequestTo(requestEntity.getRequestTo());
                if (requestEntity.getStatus() != null)
                    requestAuditEntit.setStatus(requestEntity.getStatus());
                if (requestEntity.getRequestMessage() != null)
                    requestAuditEntit.setRequestMessage(requestEntity.getRequestMessage());
                if (requestEntity.getUpdateDateTime() != null)
                    requestAuditEntit.setUpdateDateTime(requestEntity.getUpdateDateTime());
                if (requestEntity.getUserEntityFromId() != null)
                    requestAuditEntit.setUserEntityFromId(requestEntity.getUserEntityFromId());
                this.requestAuditRepository.save(requestAuditEntit);
            }
        } else {
            RequestAuditEntity requestAuditEntity = new RequestAuditEntity();
            if (requestEntity.getCreateDateTime() != null)
                requestAuditEntity.setCreateDateTime(requestEntity.getCreateDateTime());
            if (requestEntity.getFullAddress() != null)
                requestAuditEntity.setFullAddress(requestEntity.getFullAddress());
            if (requestEntity.getLatitute() != null)
                requestAuditEntity.setLatitute(requestEntity.getLatitute());
            if (requestEntity.getLongitute() != null)
                requestAuditEntity.setLongitute(requestEntity.getLongitute());
            if (requestEntity.getRequestId() != null)
                requestAuditEntity.setRequestId(requestEntity.getRequestId());
            if (requestEntity.getRequestTo() != null)
                requestAuditEntity.setRequestTo(requestEntity.getRequestTo());
            if (requestEntity.getStatus() != null)
                requestAuditEntity.setStatus(requestEntity.getStatus());
            if (requestEntity.getRequestMessage() != null)
                requestAuditEntity.setRequestMessage(requestEntity.getRequestMessage());
            if (requestEntity.getUpdateDateTime() != null)
                requestAuditEntity.setUpdateDateTime(requestEntity.getUpdateDateTime());
            if (requestEntity.getUserEntityFromId() != null)
                requestAuditEntity.setUserEntityFromId(requestEntity.getUserEntityFromId());
            this.requestAuditRepository.save(requestAuditEntity);
        }
    }

    void timeProviderWorked(RequestEntity requestEntity) {
        if (requestEntity.getStatus().intValue() >= 3) {
            RequestAuditEntity requestAuditEntit = new RequestAuditEntity();
            if (requestEntity.getRequestId() != null) {
                Integer requestAuditEntiId = Integer.valueOf(requestEntity.getRequestId().intValue() + 1);
                requestAuditEntit.setRequestAuditId(requestAuditEntiId);
            }
            List<DurationEntity> durationEntities = this.durationRepository.findByRequestAuditId(requestAuditEntit);
            Optional<UserEntity> userEntities = this.userRepository.findById(Integer.valueOf(Integer.parseInt(requestEntity.getRequestTo())));
            DurationEntity durationEntity = new DurationEntity();
            if (durationEntities != null && durationEntities.size() > 0) {
                for (DurationEntity durationEntit : durationEntities) {
                    if (durationEntit.getDurationId() != null) {
                        RequestAuditEntity requestAuditEntity = new RequestAuditEntity();
                        if (requestEntity.getCreateDateTime() != null)
                            requestAuditEntity.setCreateDateTime(requestEntity.getCreateDateTime());
                        if (requestEntity.getFullAddress() != null)
                            requestAuditEntity.setFullAddress(requestEntity.getFullAddress());
                        if (requestEntity.getLatitute() != null)
                            requestAuditEntity.setLatitute(requestEntity.getLatitute());
                        if (requestEntity.getLongitute() != null)
                            requestAuditEntity.setLongitute(requestEntity.getLongitute());
                        if (requestEntity.getRequestId() != null)
                            requestAuditEntity.setRequestId(requestEntity.getRequestId());
                        if (requestEntity.getRequestTo() != null)
                            requestAuditEntity.setRequestTo(requestEntity.getRequestTo());
                        if (requestEntity.getStatus() != null)
                            requestAuditEntity.setStatus(requestEntity.getStatus());
                        if (requestEntity.getRequestMessage() != null)
                            requestAuditEntity.setRequestMessage(requestEntity.getRequestMessage());
                        if (requestEntity.getUpdateDateTime() != null)
                            requestAuditEntity.setUpdateDateTime(requestEntity.getUpdateDateTime());
                        if (requestEntity.getUserEntityFromId() != null)
                            requestAuditEntity.setUserEntityFromId(requestEntity.getUserEntityFromId());
                        if (requestEntity.getRequestId() != null) {
                            Integer requestId = requestEntity.getRequestId();
                            requestId = Integer.valueOf(requestId.intValue() + 1);
                            requestAuditEntity.setRequestAuditId(requestId);
                        }
                        durationEntit.setStatus(requestEntity.getStatus().toString());
                        durationEntit.setRequestAuditId(requestAuditEntity);
                        durationEntit.setUserId(userEntities.get());
                        this.durationRepository.save(durationEntit);
                    }
                    calculateTimeDifference(durationEntit);
                }
            } else {
                RequestAuditEntity requestAuditEntity = new RequestAuditEntity();
                if (requestEntity.getCreateDateTime() != null)
                    requestAuditEntity.setCreateDateTime(requestEntity.getCreateDateTime());
                if (requestEntity.getFullAddress() != null)
                    requestAuditEntity.setFullAddress(requestEntity.getFullAddress());
                if (requestEntity.getLatitute() != null)
                    requestAuditEntity.setLatitute(requestEntity.getLatitute());
                if (requestEntity.getLongitute() != null)
                    requestAuditEntity.setLongitute(requestEntity.getLongitute());
                if (requestEntity.getRequestId() != null)
                    requestAuditEntity.setRequestId(requestEntity.getRequestId());
                if (requestEntity.getRequestTo() != null)
                    requestAuditEntity.setRequestTo(requestEntity.getRequestTo());
                if (requestEntity.getStatus() != null)
                    requestAuditEntity.setStatus(requestEntity.getStatus());
                if (requestEntity.getRequestMessage() != null)
                    requestAuditEntity.setRequestMessage(requestEntity.getRequestMessage());
                if (requestEntity.getUpdateDateTime() != null)
                    requestAuditEntity.setUpdateDateTime(requestEntity.getUpdateDateTime());
                if (requestEntity.getUserEntityFromId() != null)
                    requestAuditEntity.setUserEntityFromId(requestEntity.getUserEntityFromId());
                if (requestEntity.getRequestId() != null) {
                    Integer requestId = requestEntity.getRequestId();
                    requestId = Integer.valueOf(requestId.intValue() + 1);
                    requestAuditEntity.setRequestAuditId(requestId);
                }
                durationEntity.setStatus(requestEntity.getStatus().toString());
                durationEntity.setUserId(userEntities.get());
                durationEntity.setRequestAuditId(requestAuditEntity);
                this.durationRepository.save(durationEntity);
                calculateTimeDifference(durationEntity);
            }
        }
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

    public List<RequestModel> listOfRequests1(Integer userId) throws UnsupportedEncodingException {
        List<RequestModel> requestModels1 = null;
        System.out.println(requestModels1);
        HttpSession checkUserSession = this.request.getSession(false);
        String currentAddress = checkUserSession.getAttribute("currentaddress").toString();
        String latituteSession = checkUserSession.getAttribute("latitudeSession").toString();
        String longtuteSession = checkUserSession.getAttribute("longtuteSession").toString();
        UserModel userModel = this.userService.getUser(userId);
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(userModel.getUserId());
        List<RequestEntity> requestEntities = null;
        requestEntities = this.requestRepository.findRequestByUserFromId(userEntity);
        List<ServiceModel> serviceModels = this.servicePriceService.listOfServices();
        List<RequestModel> requestModels = new ArrayList<>();
        Coordinates coordinates = new Coordinates();
        coordinates.setLat(latituteSession);
        coordinates.setLng(longtuteSession);
        GeoLocation geoLocation = this.geolocationService.getGeolocationByUserAndAddress(coordinates, currentAddress);
        List<String> listofOriginAddress = geoLocation.getOrigin_addresses();
        List<String> listOfDestinationAddress = geoLocation.getDestination_addresses();
        ArrayList<RowElementsDistanceDuration> rowElementsDistanceDurations = geoLocation.getRows();
        List<UserModel> userModels = geoLocation.getUserModels();
        Distance distance = new Distance();
        Duration duration = new Duration();
        List<RequestAddressModel> requestAddressModels = new ArrayList<>();
        for (int i = 0; i < listofOriginAddress.size(); i++) {
            RequestAddressModel requestAddressModel = new RequestAddressModel();
            requestAddressModel.setUserId(((UserModel)userModels.get(i)).getUserId().toString());
            requestAddressModel.setFirstName(((UserModel)userModels.get(i)).getFirstName());
            requestAddressModel.setFirstName(((UserModel)userModels.get(i)).getLastName());
            if (((DistanceDuration)((RowElementsDistanceDuration)rowElementsDistanceDurations.get(i)).getElements().get(0)).getDuration() != null)
                requestAddressModel.setDuration(((DistanceDuration)((RowElementsDistanceDuration)rowElementsDistanceDurations.get(i)).getElements().get(0)).getDuration().getText());
            if (((DistanceDuration)((RowElementsDistanceDuration)rowElementsDistanceDurations.get(i)).getElements().get(0)).getDistance() != null)
                requestAddressModel.setDistnce(((DistanceDuration)((RowElementsDistanceDuration)rowElementsDistanceDurations.get(i)).getElements().get(0)).getDistance().getText());
            requestAddressModel.setOriginalAddress(listofOriginAddress.get(i));
            requestAddressModel.setSuburb(((String)listofOriginAddress.get(i)).split(",")[1]);
            requestAddressModel.setUserType(((UserModel)userModels.get(i)).getTypeModel().getTypeId().toString());
            requestAddressModels.add(requestAddressModel);
        }
        for (RequestEntity requestEntity : requestEntities) {
            RequestModel requestModel = new RequestModel();
            for (RequestAddressModel requestAddressModel : requestAddressModels) {
                if (requestEntity.getUserEntityFromId().getUserId().toString().equalsIgnoreCase(requestAddressModel.getUserId())) {
                    requestModel.setDistance(requestAddressModel.getDistnce());
                    requestModel.setDuration(requestAddressModel.getDuration());
                }
            }
            String[] splitMessage = requestEntity.getRequestMessage().split(",");
            BigDecimal totalPrice = new BigDecimal("0.00000");
            double sum = 0.0D;
            for (int j = 0; j < splitMessage.length; j++) {
                for (int k = 0; k < serviceModels.size(); k++) {
                    if (splitMessage[j].equalsIgnoreCase(((ServiceModel)serviceModels.get(k)).getServiceName()))
                        sum += Double.parseDouble(((ServiceModel)serviceModels.get(k)).getServicePrice().toString());
                }
            }
            requestModel.setGeoLocation(geoLocation);
            requestModel.setTotalServicePrice(Double.valueOf(sum));
            requestModel.setRequestId(requestEntity.getRequestId());
            requestModel.setRequestMessage(requestEntity.getRequestMessage());
            requestModel.setUserType(userModel.getTypeModel().getTypeId());
            requestModel.setStatus(requestEntity.getStatus());
            UserModel userFrom = this.userService.getUser(requestEntity.getUserEntityFromId().getUserId());
            requestModel.setUserFromFirstName(userFrom.getFirstName());
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            Date date = null;
            try {
                date = formatter.parse(requestEntity.getCreateDateTime().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            requestModel.setCreateDateTime(formatter.format(date));
            UserModel userModelFrom = new UserModel();
            userModelFrom.setUserId(requestEntity.getUserEntityFromId().getUserId());
            UserModel userModelTo = new UserModel();
            requestModel.setUserModelFromId(userModelFrom);
            requestModel.setUserModelToId(userModelTo);
            requestModels.add(requestModel);
        }
        return requestModels;
    }

    public ActionStatusType updateRequestByRequestId(RequestModel requestModel) throws SchedulerException {
        try {
            RequestEntity requestEntity = (RequestEntity)this.requestRepository.getOne(requestModel.getRequestId());
            requestEntity.setStatus(requestModel.getStatus());
            if (requestModel.getStatus().intValue() == 0) {
                requestEntity.setRequestTo(null);
            } else if (requestModel.getUserModelToId().getUserId() != null && requestModel.getUserModelToId().getUserId().intValue() != 0) {
                requestEntity.setRequestTo(requestModel.getUserModelToId().getUserId().toString());
            }
            UserEntity userEntity = (UserEntity)this.userRepository.getOne(requestEntity.getUserEntityFromId().getUserId());
            UserEntity userEntityTo = null;
            if (requestEntity.getRequestTo() != null && requestEntity.getRequestTo().trim().equalsIgnoreCase(""))
                userEntityTo = (UserEntity)this.userRepository.getOne(Integer.valueOf(Integer.parseInt(requestEntity.getRequestTo())));
            if (userEntity.getTypeEntityId().getTypeId().equals(Integer.valueOf(2)) && requestModel.getStatus().equals(Integer.valueOf(6))) {
                this.requestRepository.delete(requestEntity);
            } else if (requestModel.getStatus().equals(Integer.valueOf(5))) {
                this.requestRepository.delete(requestEntity);
            } else {
                this.requestRepository.save(requestEntity);
                updateRequestAudit(requestEntity);
            }
            timeProviderWorked(requestEntity);
            return new ActionStatusType(Boolean.valueOf(true), "updated successful");
        } catch (Exception e) {
            e.printStackTrace();
            return new ActionStatusType(Boolean.valueOf(true), "updated successful");
        }
    }

    public List<RequestModel> listOfRequestsByUserId(Integer userId) throws UnsupportedEncodingException {
        HttpSession checkUserSession = this.request.getSession(false);
        List<RequestModel> requestModels = new ArrayList<>();
        if (checkUserSession != null) {
            String currentAddress = checkUserSession.getAttribute("currentaddress").toString();
            String optionalAddress = checkUserSession.getAttribute("currentaddress").toString();
            String latituteSession = checkUserSession.getAttribute("latitudeSession").toString();
            String longtuteSession = checkUserSession.getAttribute("longtuteSession").toString();
            List<RequestEntity> requestEntities = new ArrayList<>();
            List<ServiceModel> serviceModels = this.servicePriceService.listOfServices();
            UserModel userModel = this.userService.getUser(userId);
            UserEntity userEntity = new UserEntity();
            userEntity.setUserId(userModel.getUserId());
            if (userModel.getTypeModel().getTypeId().intValue() == 1) {
                requestEntities = this.requestRepository.processRequest();
            } else {
                requestEntities = this.requestRepository.findRequestByUserFromId(userEntity);
            }
            List<RequestAddressModel> requestAddressModels = new ArrayList<>();
            for (int i = 0; i < requestEntities.size(); i++) {
                if (((RequestEntity)requestEntities.get(i)).getFullAddress() != null && !((RequestEntity)requestEntities.get(i)).getFullAddress().trim().equalsIgnoreCase(""))
                    optionalAddress = ((RequestEntity)requestEntities.get(i)).getFullAddress();
                if (((RequestEntity)requestEntities.get(i)).getLatitute() != null && !((RequestEntity)requestEntities.get(i)).getLatitute().trim().equalsIgnoreCase(""))
                    latituteSession = ((RequestEntity)requestEntities.get(i)).getLatitute();
                if (((RequestEntity)requestEntities.get(i)).getLongitute() != null && !((RequestEntity)requestEntities.get(i)).getLongitute().trim().equalsIgnoreCase(""))
                    longtuteSession = ((RequestEntity)requestEntities.get(i)).getLongitute();
                Coordinates coordinates = new Coordinates();
                coordinates.setLat(latituteSession);
                coordinates.setLng(longtuteSession);
                GeoLocation geoLocation = this.geolocationService.getGeolocationByRequestAddress(coordinates, currentAddress);
                RequestModel requestModel = new RequestModel();
                requestModel.setDistance(((DistanceDuration)((RowElementsDistanceDuration)geoLocation.getRows().get(i)).getElements().get(0)).getDistance().getText());
                requestModel.setDuration(((DistanceDuration)((RowElementsDistanceDuration)geoLocation.getRows().get(i)).getElements().get(0)).getDuration().getText());
                requestModel.setGeoLocation(geoLocation);
                String[] splitMessage = ((RequestEntity)requestEntities.get(i)).getRequestMessage().split(",");
                BigDecimal totalPrice = new BigDecimal("0.00000");
                double sum = 0.0D;
                for (int j = 0; j < splitMessage.length; j++) {
                    for (int k = 0; k < serviceModels.size(); k++) {
                        if (splitMessage[j].equalsIgnoreCase(((ServiceModel)serviceModels.get(k)).getServiceName()))
                            sum += Double.parseDouble(((ServiceModel)serviceModels.get(k)).getServicePrice().toString());
                    }
                }
                requestModel.setGeoLocation(geoLocation);
                requestModel.setTotalServicePrice(Double.valueOf(sum));
                requestModel.setRequestId(((RequestEntity)requestEntities.get(i)).getRequestId());
                requestModel.setRequestMessage(((RequestEntity)requestEntities.get(i)).getRequestMessage());
                requestModel.setUserType(userModel.getTypeModel().getTypeId());
                requestModel.setStatus(((RequestEntity)requestEntities.get(i)).getStatus());
                UserModel userModelTo = new UserModel();
                if (((RequestEntity)requestEntities.get(i)).getRequestTo() != null && !((RequestEntity)requestEntities.get(i)).getRequestTo().trim().isEmpty())
                    userModelTo.setUserId(Integer.valueOf(Integer.parseInt(((RequestEntity)requestEntities.get(i)).getRequestTo())));
                requestModel.setUserModelToId(userModelTo);
                UserModel userFrom = this.userService.getUser(((RequestEntity)requestEntities.get(i)).getUserEntityFromId().getUserId());
                requestModel.setUserFromFirstName(userFrom.getFirstName());
                if (userModelTo != null && userModelTo.getUserId() != null && userModelTo.getUserId().intValue() != 0) {
                    UserModel userTo = this.userService.getUser(userModelTo.getUserId());
                    requestModel.setUserToFirstName(userTo.getFirstName());
                    requestModel.setUserToProfilePicture(userTo.getUserProfilePicture());
                    if (userTo.getTelephoneModel() != null && userTo.getTelephoneModel().getTelephoneNumber() != null && !userTo.getTelephoneModel().getTelephoneNumber().isEmpty())
                        requestModel.setUserToPhoneNumber(userTo.getTelephoneModel().getTelephoneNumber());
                }
                if (userFrom != null && userModelTo.getUserId() != null && userFrom.getUserId().intValue() != 0) {
                    UserModel userFromT = this.userService.getUser(userFrom.getUserId());
                    requestModel.setUserFromProfilePicture(userFromT.getUserProfilePicture());
                    if (userFromT.getTelephoneModel() != null && userFromT.getTelephoneModel().getTelephoneNumber() != null && !userFromT.getTelephoneModel().getTelephoneNumber().isEmpty())
                        requestModel.setUserFromPhoneNumber(userFromT.getTelephoneModel().getTelephoneNumber());
                }
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                Date date = null;
                try {
                    date = formatter.parse(((RequestEntity)requestEntities.get(i)).getCreateDateTime().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                requestModel.setCreateDateTime(formatter.format(date));
                UserModel userModelFrom = new UserModel();
                userModelFrom.setUserId(((RequestEntity)requestEntities.get(i)).getUserEntityFromId().getUserId());
                requestModel.setUserModelFromId(userModelFrom);
                requestModels.add(requestModel);
            }
        }
        return requestModels;
    }

    public List<RequestModel> listOfRequests() throws UnsupportedEncodingException {
        List<RequestEntity> requestEntities = this.requestRepository.findAll();
        List<RequestModel> requestModels = new ArrayList<>();
        for (RequestEntity requestEntity : requestEntities) {
            RequestModel requestModel = new RequestModel();
            requestModel.setRequestId(requestEntity.getRequestId());
            requestModel.setRequestMessage(requestModel.getRequestMessage());
            UserModel UserModelFromId = new UserModel();
            UserModel UserModelToId = new UserModel();
            requestModel.setUserModelFromId(UserModelFromId);
            requestModel.setUserModelToId(UserModelToId);
            requestModel.setFullAddress(requestEntity.getFullAddress());
            requestModels.add(requestModel);
        }
        return requestModels;
    }

    public List<RequestModel> listOfRequestsByStatus() throws UnsupportedEncodingException {
        List<RequestEntity> requestEntities = this.requestRepository.processRequest();
        List<RequestModel> requestModels = new ArrayList<>();
        for (RequestEntity requestEntity : requestEntities) {
            RequestModel requestModel = new RequestModel();
            requestModel.setRequestId(requestEntity.getRequestId());
            requestModel.setRequestMessage(requestModel.getRequestMessage());
            UserModel UserModelFromId = new UserModel();
            UserModel UserModelToId = new UserModel();
            requestModel.setUserModelFromId(UserModelFromId);
            requestModel.setUserModelToId(UserModelToId);
            requestModel.setFullAddress(requestEntity.getFullAddress());
            requestModels.add(requestModel);
        }
        return requestModels;
    }
}
