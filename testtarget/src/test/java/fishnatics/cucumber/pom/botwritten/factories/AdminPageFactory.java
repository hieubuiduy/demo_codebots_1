package fishnatics.cucumber.pom.botwritten.factories;

import fishnatics.cucumber.pom.botwritten.page.*;
import fishnatics.cucumber.pom.botwritten.page.crudlist.*;
import com.google.inject.Inject;
import cucumber.runtime.java.guice.ScenarioScoped;
import java.util.Properties;
import lombok.*;
import org.openqa.selenium.WebDriver;

@ScenarioScoped
public class AdminPageFactory {

	@Inject
	protected WebDriver webDriver;

	@Inject
	protected Properties properties;

	@Inject
	EntitiesTankListPage entitiesTankListPage;
	@Inject
	EntitiesFishListPage entitiesFishListPage;
	@Inject
	UsersFishnaticListPage usersFishnaticListPage;
	@Inject
	EntitiesSpeciesListPage entitiesSpeciesListPage;
	@Inject
	UsersAdminListPage usersAdminListPage;


	public CrudListPage createCrudPage(String name) throws Exception {
		switch (name) {

			case "Tank":
				return entitiesTankListPage;
			case "Fish":
				return entitiesFishListPage;
			case "Fishnatic":
				return usersFishnaticListPage;
			case "Species":
				return entitiesSpeciesListPage;
			case "Admin":
				return usersAdminListPage;
			default :
				throw new Exception(String.format("Unexpected Crud list Page: %s", name));
		}
	}
}
