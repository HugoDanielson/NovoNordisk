package Camera;

import java.util.Collection;

import com.kuka.task.ITask;
import com.kuka.task.ITaskFunctionMonitor;
import com.kuka.task.ITaskInstance;
import com.kuka.task.ITaskManager;
import com.kuka.task.TaskFunctionMonitor;

public class CameraInit {
	private IcameraAPI iCammeraAPI;
	private Integer programNr;
	private ITaskFunctionMonitor cameraMonitor;
	private Collection<ITaskInstance> instances;
	private ITask task;
	private ITaskManager taskManager;
	private CameraAPIbackground camera;

	public CameraInit(IcameraAPI iCammeraAPI, Integer programNr, ITaskManager taskManager, CameraAPIbackground camera) {
		this.iCammeraAPI = iCammeraAPI;
		this.programNr = programNr;
		this.taskManager = taskManager;
		this.camera = camera;
	}

	public void run() {
		// iCammeraAPI = getTaskFunction(IcameraAPI.class);

		cameraMonitor = TaskFunctionMonitor.create(iCammeraAPI);

		if (cameraMonitor.isAvailable()) {
			camera = iCammeraAPI.getCameraApi();
		} else {
			System.out.println("Creating new instance of Camera");
			task = taskManager.getTask(CameraAPIbackground.class);
			instances = task.getInstances();

			if (instances.isEmpty()) {
				try {
					task.startInstance();
					System.out.println("Start camera Background");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
			camera = iCammeraAPI.getCameraApi();
			System.out.println("Camera instance = " + camera);

		}
		Thread tCamera = new Thread(new Runnable() {

			@Override
			public void run() {
				iCammeraAPI.getCameraApi().changeProgramNr(programNr);
			}
		});
		tCamera.start();
	}


	public CameraAPIbackground getCamera() {
		return camera;
	}
}