package Util;



	import javax.inject.Inject;

	import com.kuka.roboticsAPI.deviceModel.LBR;
	import com.kuka.roboticsAPI.geometricModel.AbstractFrame;
	import com.kuka.roboticsAPI.geometricModel.Frame;
	import com.kuka.roboticsAPI.geometricModel.ObjectFrame;
	import com.kuka.roboticsAPI.geometricModel.math.Transformation;
	import com.kuka.roboticsAPI.persistenceModel.IPersistenceEngine;
	import com.kuka.roboticsAPI.persistenceModel.XmlApplicationDataSource;
	import com.kuka.task.ITaskLogger;

	public class BaseManipulation {
		@Inject private LBR lbr;
		@Inject private ITaskLogger logger;
		
		
		public BaseManipulation(){
			
		}
		public BaseManipulation(LBR lbr, ITaskLogger logger) {
			this.lbr = lbr;
			this.logger = logger;
		}
		public void changeBase(ObjectFrame baseToManipulate, AbstractFrame reference, double X, double Y, double Z, double A, double B, double C)

		{

			// _logger.info("BASEMEARUMENT(): Old Base: Coordinates: " + "\nX = " +
			// baseToManipulate.getX() + "\nY = " + baseToManipulate.getY() + "\nZ = " +
			// baseToManipulate.getZ() + "\nA = " +
			// Math.toDegrees(baseToManipulate.getAlphaRad()) + "\nB = " +
			// Math.toDegrees(baseToManipulate.getBetaRad()) + "\nC = " +
			// Math.toDegrees(baseToManipulate.getGammaRad()));

			Frame manipulatedBase;
			manipulatedBase = reference.copyWithRedundancy().transform(Transformation.ofDeg(X, Y, Z, A, B, C));

			// _logger.info("BASEMEARUMENT(): New Base: Coordinates: " + "\nX = " +
			// manipulatedBase.getX() + "\nY = " + manipulatedBase.getY() + "\nZ = " +
			// manipulatedBase.getZ() + "\nA = " +
			// Math.toDegrees(manipulatedBase.getAlphaRad()) + "\nB = " +
			// Math.toDegrees(manipulatedBase.getBetaRad()) + "\nC = " +
			// Math.toDegrees(manipulatedBase.getGammaRad()));

			final IPersistenceEngine engine = lbr.getContext().getEngine(IPersistenceEngine.class);
			final XmlApplicationDataSource defaultDataSource = (XmlApplicationDataSource) engine.getDefaultDataSource();
			defaultDataSource.changeFrameTransformation(baseToManipulate, baseToManipulate.getParent().transformationTo(manipulatedBase));
			defaultDataSource.saveFile(true);

		}

		public void shiftBase(ObjectFrame baseToManipulate, AbstractFrame reference, double offset_X_manipulate, double offset_Y_manipulate, double offset_Z_manipulate, double offset_A_manipulate, double offset_B_manipulate, double offset_C_manipulate)

		{
			StringBuilder message = new StringBuilder();
		
			Frame manipulatedBase;
			manipulatedBase = baseToManipulate.copyWithRedundancy().transform(reference, Transformation.ofDeg(offset_X_manipulate, offset_Y_manipulate, offset_Z_manipulate, offset_A_manipulate, offset_B_manipulate, offset_C_manipulate));

//			message.append("BASEMEARUMENT(): New Base: Coordinates: " + "\nX = " ); 
//			message.append(manipulatedBase.getX() + "\nY = " + manipulatedBase.getY() + "\nZ = " ); 
//			message.append(manipulatedBase.getZ() + "\nA = " ); 
//			message.append(Math.toDegrees(manipulatedBase.getAlphaRad()) + "\nB = " ); 
//			message.append(Math.toDegrees(manipulatedBase.getBetaRad()) + "\nC = " ); 
//			message.append(Math.toDegrees(manipulatedBase.getGammaRad())+"\n"); 
//
//			
//			logger.info(message.toString());

			final IPersistenceEngine engine = lbr.getContext().getEngine(IPersistenceEngine.class);
			final XmlApplicationDataSource defaultDataSource = (XmlApplicationDataSource) engine.getDefaultDataSource();
			defaultDataSource.changeFrameTransformation(baseToManipulate, baseToManipulate.getParent().transformationTo(manipulatedBase));
			defaultDataSource.saveFile(true);

		}
		
		public void DeleteOldData(ObjectFrame baseToManipulate){
			Frame manipulatedBase;
			
			manipulatedBase = baseToManipulate.copyWithRedundancy();
			manipulatedBase.setX(0.0);
			manipulatedBase.setY(0.0);
			manipulatedBase.setZ(0.0);
			manipulatedBase.setAlphaRad(0.0);
			manipulatedBase.setBetaRad(0.0);
			manipulatedBase.setGammaRad(0.0);
			
					
			final IPersistenceEngine engine = lbr.getContext().getEngine(IPersistenceEngine.class);
			final XmlApplicationDataSource defaultDataSource = (XmlApplicationDataSource) engine.getDefaultDataSource();
			defaultDataSource.changeFrameTransformation(baseToManipulate, baseToManipulate.getParent().transformationTo(manipulatedBase));
			
			//defaultDataSource.changeFrameTransformation(baseToManipulate, baseToManipulate.getParent().transformationTo(manipulatedBase));
			defaultDataSource.saveFile(true);

			
		}
		
		public void setBase(ObjectFrame baseToManipulate,double X,double Y,double Z,double A,double B,double C){
			Frame manipulatedBase;
			
			manipulatedBase = baseToManipulate.copyWithRedundancy();
			manipulatedBase.setX(X);
			manipulatedBase.setY(Y);
			manipulatedBase.setZ(Z);
			manipulatedBase.setAlphaRad(Math.toRadians(A));
			manipulatedBase.setBetaRad(Math.toRadians(B));
			manipulatedBase.setGammaRad(Math.toRadians(C));
			
					
			final IPersistenceEngine engine = lbr.getContext().getEngine(IPersistenceEngine.class);
			final XmlApplicationDataSource defaultDataSource = (XmlApplicationDataSource) engine.getDefaultDataSource();
			defaultDataSource.changeFrameTransformation(baseToManipulate, baseToManipulate.getParent().transformationTo(manipulatedBase));
			
			//defaultDataSource.changeFrameTransformation(baseToManipulate, baseToManipulate.getParent().transformationTo(manipulatedBase));
			defaultDataSource.saveFile(true);
			

			
		}
	}
