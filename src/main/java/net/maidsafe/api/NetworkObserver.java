package net.maidsafe.api;

import java.util.concurrent.CompletableFuture;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;

import net.maidsafe.api.model.App;
import net.maidsafe.binding.model.FfiCallback;

public abstract class NetworkObserver {

	private NetworkObserver instance;
	private final PointerByReference appPointerRef = new PointerByReference();
	private final CompletableFuture<SafeClient> future = new CompletableFuture<SafeClient>();
	private App app;
	
	final FfiCallback.NetworkObserverCallback observer = new FfiCallback.NetworkObserverCallback() {
		
		@Override
		public void onResponse(Pointer userData, int errorCod, int event) {			
			if (!future.isDone()) {
				app.setAppHandle(appPointerRef.getValue());
				future.complete(new SafeClient(app));
			}
			instance.onStateChange(event);
		}
	};
	
	
	public NetworkObserver() {
		instance = this;
	}
	
	public void setApp(App app) {
		this.app = app;
	}
	
	public PointerByReference getAppRef() {
		return appPointerRef;
	}
	
	public FfiCallback.NetworkObserverCallback getObserver() {
		return observer;
	}
	
	public CompletableFuture<SafeClient> getFuture() {
		return future;
	}
	
	public abstract void onStateChange(int event);
	
	public abstract void onError(int errorCode);
	
}
