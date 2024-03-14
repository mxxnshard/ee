package web.service.digest;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

@Service
public class DigestMd5 implements Digest {
    @Override
    public String hex(String pass) {
        return DigestUtils.md5Hex(pass);
    }
}
