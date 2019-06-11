package fr.istic.galaxsim.gui;

import fr.istic.galaxsim.data.Coordinate;
import fr.istic.galaxsim.data.CosmosElement;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Point3D;
import javafx.scene.shape.Shape3D;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Path3DTransition extends Transition {

    public final ObjectProperty<Duration> durationProperty = new SimpleObjectProperty<>();

    private final Shape3D shape;

    private final Coordinate initialPosition;
    private final double totalDistance;
    private final ArrayList<Point3D> points = new ArrayList<>();
    private Queue<Point3D> targets = new ConcurrentLinkedQueue<>();

    private double speed;

    private Point3D currentTarget;
    private double lastDistance = 0.0;

    public Path3DTransition(Shape3D shape, CosmosElement element) {
        super(30);
        this.shape = shape;
        double totalDistanceTemp = 0.0;

        initialPosition = element.getCoordinate(0);
        Coordinate lastCoord = initialPosition;

        for(int i = 2;i < element.getSizeCoordinate();i++) {
            Coordinate coord = element.getCoordinate(i);
            Point3D p = new Point3D(coord.getX(), coord.getY(), coord.getZ());

            totalDistanceTemp += p.distance(lastCoord.getX(), lastCoord.getY(), lastCoord.getZ());
            lastCoord = coord;
            points.add(p);
            targets.add(p);
        }
        this.totalDistance = totalDistanceTemp;

        if(!targets.isEmpty()) {
            currentTarget = targets.remove();
            lastDistance = new Point3D(initialPosition.getX(), initialPosition.getY(), initialPosition.getZ()).distance(currentTarget);
        }

        durationProperty.addListener((obs, oldValue, newValue) -> {
            setCycleDuration(newValue);
            speed = totalDistance / newValue.toSeconds() /*/ 30*/;
        });
    }

    private Point3D getShapePosition() {
        return new Point3D(shape.getTranslateX(), shape.getTranslateY(), shape.getTranslateZ());
    }

    @Override
    protected void interpolate(double v) {
        Point3D shapePosition = getShapePosition();
        double d = shapePosition.distance(currentTarget) - 1.0;
        if(lastDistance > d) {
            Point3D direction = currentTarget.subtract(shapePosition).normalize();
            direction = direction.multiply(speed/30);

            shape.setTranslateX(shape.getTranslateX() + direction.getX());
            shape.setTranslateY(shape.getTranslateY() + direction.getY());
            shape.setTranslateZ(shape.getTranslateZ() + direction.getZ());
            lastDistance = d;
        }
        else {
            if(!targets.isEmpty()) {
                currentTarget = targets.remove();
                lastDistance = currentTarget.distance(shapePosition);
            }
        }
    }

    public void resetInitialPosition() {
        shape.setTranslateX(initialPosition.getX());
        shape.setTranslateY(initialPosition.getY());
        shape.setTranslateZ(initialPosition.getZ());
    }

    public void resetTargets() {
        for(int i = 1;i < points.size();i++) {
            targets.add(points.get(i));
        }
    }
    
    
    
    /**
     * réinitialise la transition à un temps donné
     * @param temps de la simulation
     */
    private void resetTargets(int k) {
    	for(int i = k;i < points.size();i++) {
            targets.add(points.get(i));
        }
		
	}
    
    /**
     * @param temps auquel se positionner
     */
    public void setTransitionPosition(double t) {
    	
    	targets.clear();
    	
    	
    	
        double distanceFromStart =( t * speed) ;
        double d = 0.0;
        Point3D lastPoint = initialPosition.getPoint3D();
        
        for(Point3D p : points) {
            d += lastPoint.distance(p);
            if(d >= distanceFromStart) {
            	
                break;
            }
            lastPoint = p;
        }
        
        
        
        resetTargets((int )this.points.indexOf(lastPoint)+1  );
        

        shape.setTranslateX(lastPoint.getX());
        shape.setTranslateY(lastPoint.getY());
        shape.setTranslateZ(lastPoint.getZ());
        
        this.playFrom(Duration.seconds(t));
        
    }
    
    

	public void setNextTarget(double d) {
    	
    }
}
