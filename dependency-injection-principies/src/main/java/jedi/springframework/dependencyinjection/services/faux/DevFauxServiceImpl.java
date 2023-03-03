package jedi.springframework.dependencyinjection.services.faux;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile({"dev","default"})
@Service("fauxService")
public class DevFauxServiceImpl implements FauxService {
    @Override
    public String dataFromDataSource() {
        return "Data from DEV environment";
    }

}
