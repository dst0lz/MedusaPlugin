package eu.thelair.medusa;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.ext.bridge.node.CloudNetBridgeModule;
import de.dytanic.cloudnet.ext.syncproxy.node.CloudNetSyncProxyModule;
import de.dytanic.cloudnet.wrapper.Wrapper;
import de.dytanic.cloudnet.wrapper.tweak.CloudNetTweaker;
import eu.thelair.medusa.command.CreateNeptunCommand;
import eu.thelair.medusa.command.MedusaToggleCommand;
import eu.thelair.medusa.command.ReportCommand;
import eu.thelair.medusa.command.StaffnotifyCommand;
import eu.thelair.medusa.database.MySQL;
import eu.thelair.medusa.listener.ChatListener;
import eu.thelair.medusa.listener.ClickListener;
import eu.thelair.medusa.listener.QuitListener;
import eu.thelair.medusa.listener.ViolationListener;
import eu.thelair.medusa.manager.MessageManager;
import eu.thelair.medusa.manager.ReportManager;
import eu.thelair.medusa.manager.StorageManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Medusa extends JavaPlugin {
  public static final String PREFIX = "§4The§fLair §8• §r";
  public static final String OWN_PREFIX = "§8[§cMedusa§8]§7 ";

  private Injector injector;

  //Managers
  @Inject
  private ReportManager reportManager;
  @Inject
  private MessageManager messageManager;
  @Inject
  private StorageManager storageManager;

  //Listener
  @Inject
  private ClickListener clickListener;
  @Inject
  private ViolationListener violationListener;
  @Inject
  private QuitListener quitListener;
  @Inject
  private ChatListener chatListener;

  //Commands
  @Inject
  private ReportCommand reportCommand;
  @Inject
  private StaffnotifyCommand staffnotifyCommand;
  @Inject
  private MedusaToggleCommand medusaToggleCommand;
  @Inject
  private CreateNeptunCommand createNeptunCommand;

  //Utils
  @Inject
  private MySQL mySQL;

  @Override
  public void onEnable() {
    init();
    execute();
    registerCommands();
    registerEvents();
    Bukkit.getMessenger().registerOutgoingPluginChannel(this, "AntiHack");
    Bukkit.getConsoleSender().sendMessage(PREFIX + "§aerfolgreich aktiviert!");
  }

  @Override
  public void onDisable() {
    Bukkit.getMessenger().unregisterOutgoingPluginChannel(this, "AntiHack");
    Bukkit.getConsoleSender().sendMessage(PREFIX + "§cerfolgreich deaktiviert!");
  }

  private void init() {
    MedusaModule medusaModule = new MedusaModule(this);
    this.injector = Guice.createInjector(medusaModule);
    this.injector.injectMembers(this);
  }

  private void execute() {
    messageManager.startTask();
  }

  private void registerCommands() {
    Bukkit.getPluginCommand("report").setExecutor(reportCommand);
    Bukkit.getPluginCommand("staffnotify").setExecutor(staffnotifyCommand);
    Bukkit.getPluginCommand("medusatoggle").setExecutor(medusaToggleCommand);
    Bukkit.getPluginCommand("createneptun").setExecutor(createNeptunCommand);
  }

  private void registerEvents() {
    PluginManager pm = Bukkit.getPluginManager();
    pm.registerEvents(clickListener, this);
    pm.registerEvents(violationListener, this);
    pm.registerEvents(quitListener, this);
    pm.registerEvents(chatListener, this);
  }

  public static Medusa getInstance() {
    return Medusa.getPlugin(Medusa.class);
  }
}
