package jedi.springframework.dependencyinjection.services.faux;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile({"uat"})
@Service("fauxService")
public class UatFauxServiceImpl implements FauxService {

    @Override
    public String dataFromDataSource() {
        return "Data from UAT environment";
    }

}
