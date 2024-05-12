package za.com.cocamzansi.service;

import java.util.List;
import za.com.cocamzansi.model.DurationModel;

public interface DurationService {
    DurationModel findByRequestIdAndUserId(Integer paramInteger1, Integer paramInteger2);

    List<DurationModel> findRequestByUserId(Integer paramInteger);
}
