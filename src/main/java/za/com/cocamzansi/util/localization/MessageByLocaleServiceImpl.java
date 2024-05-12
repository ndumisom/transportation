package za.com.cocamzansi.util.localization;

import org.springframework.stereotype.Service;

@Service
public class MessageByLocaleServiceImpl implements MessageByLocaleService {

    public MessageByLocaleServiceImpl(){

    }

    @Override
    public String getMessage(String code) {
        return code;
    }

    @Override
    public String getMessage(String var1, Object[] var2) {
        return null;
    }

    @Override
    public String getMessage(String var1, String var2) {
        return null;
    }
}
