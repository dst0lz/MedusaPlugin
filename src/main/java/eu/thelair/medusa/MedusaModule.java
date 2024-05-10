package eu.thelair.medusa;

import com.google.inject.AbstractModule;
import de.jpx3.intave.IntaveAccessor;
import de.jpx3.intave.access.IntaveAccess;

public class MedusaModule extends AbstractModule {

  private final Medusa plugin;

  public MedusaModule(Medusa plugin) {
    this.plugin = plugin;
  }

  @Override
  protected void configure() {
    bind(Medusa.class).toInstance(plugin);
    bind(IntaveAccess.class).toInstance(IntaveAccessor.unsafeAccess());
  }

}
