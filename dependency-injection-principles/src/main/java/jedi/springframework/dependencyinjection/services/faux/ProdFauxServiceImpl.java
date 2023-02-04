package jedi.springframework.dependencyinjection.services.faux;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("prod")
@Service("fauxService")
public class ProdFauxServiceImpl implements FauxService {
    @Override
    public String dataFromDataSource() {
        return "Data from PROD environment";
    }
}
