package jedi.springframework.dependencyinjection.services.faux;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("qa")
@Service("fauxService")
public class QAFauxServiceImpl implements FauxService {
    @Override
    public String dataFromDataSource() {
        return "Data from QA environment";
    }
}
