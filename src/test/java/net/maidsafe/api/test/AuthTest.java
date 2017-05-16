package net.maidsafe.api.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

import net.maidsafe.api.Auth;
import net.maidsafe.api.NetworkObserver;
import net.maidsafe.api.SafeClient;
import net.maidsafe.api.model.AppInfo;
import net.maidsafe.api.model.ContainerPermission;
import net.maidsafe.api.model.Permission;
import junit.framework.TestCase;

public class AuthTest extends TestCase {
	
	public void testGetAuthURIWithoutNullArguments() throws Exception {
		Auth auth;		
		CompletableFuture<String> res;		
		auth = new Auth();
		
		res = auth.getURI(null, null);
		try {
			res.get();
		} catch(Exception e) {
			assert (e != null);
		}		
	}

	public void testGetAuthURIWithoutPermissionsAsNull() throws Exception {
		Auth auth;
		AppInfo appInfo;
		CompletableFuture<String> res;
		String actual;
		auth = new Auth();
		appInfo = new AppInfo("com.maidsafe.test", "demo_app", "MaidSafe");
		res = auth.getURI(appInfo, null);
		actual = res.get();
		assert (actual != null);
		assert (actual.startsWith("safe-auth:"));
	}

	public void testGetAuthURIWithoutPermissionsAsEmptyList() throws Exception {
		Auth auth;
		AppInfo appInfo;
		CompletableFuture<String> res;
		String actual;
		auth = new Auth();
		appInfo = new AppInfo("com.maidsafe.test", "demo_app", "MaidSafe");
		res = auth.getURI(appInfo, new ArrayList<ContainerPermission>());
		actual = res.get();
		assert (actual != null);
		assert (actual.startsWith("safe-auth:"));
	}
	
	public void testGetAuthURIWithAConatiner() throws Exception {
		Auth auth;
		AppInfo appInfo;
		CompletableFuture<String> res;
		ContainerPermission permission;
		String actual;
		auth = new Auth();
		appInfo = new AppInfo("com.maidsafe.test", "demo_app", "MaidSafe");
		permission = new ContainerPermission("_public", Arrays.asList(Permission.Insert));
		res = auth.getURI(appInfo, Arrays.asList(permission));
		actual = res.get();
		assert (actual != null);
		assert (actual.startsWith("safe-auth:"));
	}
	
	public void testGetAuthURIWithMultipleContainers() throws Exception {
		Auth auth;
		AppInfo appInfo;
		CompletableFuture<String> res;
		ContainerPermission publicPermission;
		ContainerPermission photosPermission;
		String actual;
		auth = new Auth();
		appInfo = new AppInfo("com.maidsafe.test", "demo_app", "MaidSafe");
		publicPermission = new ContainerPermission("_public", Arrays.asList(Permission.Read));
		photosPermission = new ContainerPermission("_photos", Arrays.asList(Permission.Read));
		res = auth.getURI(appInfo, Arrays.asList(publicPermission, photosPermission));
		actual = res.get();
		assert (actual != null);
		assert (actual.startsWith("safe-auth:"));
	}
	
	public void testGetAuthURIWithFullPermission() throws Exception {
		Auth auth;
		AppInfo appInfo;
		CompletableFuture<String> res;
		ContainerPermission publicPermission;
		ContainerPermission photosPermission;
		String actual;
		auth = new Auth();
		appInfo = new AppInfo("com.maidsafe.test", "demo_app", "MaidSafe");
		publicPermission = new ContainerPermission("_public", Arrays.asList(Permission.Insert, Permission.ManagePermissions));
		photosPermission = new ContainerPermission("_photos", Arrays.asList(Permission.Read, Permission.Insert, Permission.Update, Permission.Delete, Permission.ManagePermissions));
		res = auth.getURI(appInfo, Arrays.asList(publicPermission, photosPermission));
		actual = res.get();
		assert (actual != null);
		assert (actual.startsWith("safe-auth:"));
	}
		
	public void testGetConatinerRequestURIWithoutContainers() throws Exception {
		Auth auth;
		AppInfo appInfo;
		CompletableFuture<String> res;		
		
		auth = new Auth();		
		appInfo = new AppInfo("com.maidsafe.test", "demo_app", "MaidSafe");		
		res = auth.getContainerRequestURI(appInfo, null);
		try {
			res.get();
		} catch (Exception e) {
			assert (e != null);
		}		
	}
	
	public void testGetConatinerRequestURI() throws Exception {
		Auth auth;
		AppInfo appInfo;
		CompletableFuture<String> res;
		ContainerPermission publicPermission;
		ContainerPermission photosPermission;
		String actual;
		auth = new Auth();
		appInfo = new AppInfo("com.maidsafe.test", "demo_app", "MaidSafe");
		publicPermission = new ContainerPermission("_public", Arrays.asList(Permission.Insert, Permission.ManagePermissions));
		photosPermission = new ContainerPermission("_photos", Arrays.asList(Permission.Read, Permission.Insert, Permission.Update, Permission.Delete, Permission.ManagePermissions));
		res = auth.getContainerRequestURI(appInfo, Arrays.asList(publicPermission, photosPermission));
		actual = res.get();
		assert (actual != null);
		assert (actual.startsWith("safe-auth:"));
	}
	
//	public void testRegisteredAppConnection() throws Exception {
//		final String uri = "safe-bmv0lm1hawrzywzllmv4yw1wbgvzlm1krxhhbxbszq:AQAAAOIDI_gAAAAAAAAAACAAAAAAAAAAGWzDHH2GG-TUtS_qLvytHNXrAPWGtI6QLDuoP28EE_0gAAAAAAAAALPyoRvbtvPKs9bWYgsQvT3strDfQsw4HXRzNW_cfmxTIAAAAAAAAAD_a6ysxSGIUWz9pOLlq9hRMM-EJQctDpVkhRTXPar-W0AAAAAAAAAA-O8HsVV5ZZbiAwWTTFXQeNX7pSYtLmZXRHnrdVyXZvv_a6ysxSGIUWz9pOLlq9hRMM-EJQctDpVkhRTXPar-WyAAAAAAAAAAUnTeCf39C-KDfioarbgDedqYhu_ZEpCHK_CatkiYNFUgAAAAAAAAAOTkFE7GibxaH0egTV1NtczggZkyAsCVRY6AcbceiSNfAAAAAAAAAAAAAAAAAAAAAAAAAAAAMCralz2EJh0ML2wMZLBhh0hELI1dIQUlVtaWHqIClqmYOgAAAAAAABgAAAAAAAAA2lo16ByCIq4SnojMIRPV_RSvQIOelGUD";
//		Auth auth;
//		
//		auth = new Auth();
//		AppInfo appInfo = new AppInfo("com.maidsafe.test", "demo_app", "MaidSafe");
//		CompletableFuture<SafeClient> result = auth.connectWithURI(appInfo, uri, new NetworkObserver() {
//			
//			@Override
//			public void onStateChange(int event) {
//				// FIXME use actual enum 
//				assert (event == 0);
//			}
//			
//			@Override
//			public void onError(int errorCode) {
//				assert (errorCode == 0);
//			}
//		});
//		assert (result.get() instanceof SafeClient);
//	}

}