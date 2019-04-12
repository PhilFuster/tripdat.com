package dev.phasterinc.tripdat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

/**
 * TripDat Travel Itinerary application
 * Author: Philip Fuster
 */


@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
public class TripdatApplication {

	public static void main(String[] args) {
		SpringApplication.run(TripdatApplication.class, args);
	}

}
