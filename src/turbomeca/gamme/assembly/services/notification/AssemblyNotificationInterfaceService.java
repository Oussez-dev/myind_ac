package turbomeca.gamme.assembly.services.notification;

import javax.jws.WebService;

import org.springframework.stereotype.Service;

import turbomeca.gamme.ecran.services.notification.service.impl.NotificationInterfaceService;
 

@WebService(serviceName = "NotificationInterfaceServiceService",
targetNamespace = "http://impl.service.notification.services.ecran.gamme.turbomeca/",
endpointInterface = "turbomeca.gamme.ecran.services.notification.INotificationInterfaceService"
)

@Service(value="assemblyNotificationInterfaceService")
public class AssemblyNotificationInterfaceService extends NotificationInterfaceService {

}
