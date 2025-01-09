package it.epicode.fe_07_24_sp2_2.veicoli;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VeicoloRunner implements ApplicationRunner {
    Faker faker = new Faker();

    private final VeicoloSrv veicoloSrv;
    private final VeicoloRepo veicoloRepo;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        if(veicoloRepo.count()>300){
            return;
        }

        for (int i = 0; i <300; i++) {
            Veicolo v = new Veicolo();
            v.setMarca(faker.company().name());
            v.setModello(faker.company().name());
            v.setTarga(faker.letterify("??###??"));
            veicoloRepo.save(v);

        }

    }
}
