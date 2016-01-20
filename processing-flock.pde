        import java.util.*;
        
        // constants
        final static String NEAREST="avoidance according to nearest neighbour";
        final static String NEAREST_SIX="avoidance according to 6 nearest neighbours";
        
          Flock flock;
        
          // configuration data
          int boidNumber = 70;
          float minSeparation = 20f; // hard limit on separation, in paper it is 0.2m, here we put it to 20 units to make it visible
          String separationType=NEAREST_SIX;
          int simStepCount=0; // count simulation steps, to capture statistics 
          HashSet<Float> nearestNeighborDistanceStat = new HashSet<Float>(); 
          
          void setup() {
            size(700, 700);
            flock = new Flock();
            // Add an initial set of boids into the system
            for (int i = 0; i < boidNumber; i++) {
              flock.addBoid(new Boid(random(300),random(300)));
            }
          }
          
          void draw() {
            background(50);
            flock.run();
          }
          
          // Add a new boid into the System
          void mousePressed() {
            flock.addBoid(new Boid(mouseX,mouseY));
          }
          
          // The Flock (a list of Boid objects)    
          class Flock {
            ArrayList<Boid> boids; // An ArrayList for all the boids
          
            Flock() {
              boids = new ArrayList<Boid>(); // Initialize the ArrayList
            }
          
            void run() {
              for (Boid b : boids) {
                b.run(boids);  // Passing the entire list of boids to each boid individually
              }
            }
          
            void addBoid(Boid b) {
              boids.add(b);
            }
          
          }
          
          // The Boid class
          class Boid {
            PVector location;
            PVector velocity;
            PVector acceleration;
            float r;
            float maxforce;    // Maximum steering force
            float maxspeed;    // Maximum speed
          
            Boid(float x, float y) {
              acceleration = new PVector(0, 0);
          
              float angle = random(TWO_PI);
              velocity = new PVector(cos(angle), sin(angle));
              location = new PVector(x, y);
              r = 2.0;
              maxspeed = 2;
              maxforce = 0.03;
            }
          
            void run(ArrayList<Boid> boids) {
              flock(boids);
              update();
              borders();
              render();
      
              captureStatistics(boids); // custom method to save the nearest distance  
            }
          
            void applyForce(PVector force) {
              // We could add mass here if we want A = F / M
              acceleration.add(force);
            }
      
            void captureStatistics(ArrayList<Boid> boids) {
              // for each boid in boids, find nearest neighbor and save it
              // save only if simulation has executed more than 100000 steps (to reach stable simulation)
              // and then, every 20 steps
                
              simStepCount++;   
                  
              if (simStepCount>10000 && simStepCount%1000==0) {
                for(Boid curBoid : boids) {
                      
                  // calculate the distances to neighbors
                  HashSet<Float> distances=new HashSet<Float>();
                  for (Boid boid : boids) {
                    float d = Math.abs(PVector.dist(curBoid.location, boid.location));
                    distances.add(d);
                  }
                    
                  List<Float> sortedDistances = new ArrayList(distances);
                  Collections.sort(sortedDistances);
                  float nearestNeighborDistance = sortedDistances.get(1);
                  nearestNeighborDistanceStat.add(nearestNeighborDistance);
                      
                }                    
              }
             // output average nearest neighbor distance every 1000 simulation steps 
             if (simStepCount>50000) {
                float sum=0;
                for (Float d:nearestNeighborDistanceStat) {
                  sum+=d;
                }
                sum=sum/nearestNeighborDistanceStat.size();
                System.out.println(" ***** AVERAGE DISTANCE TO NEAREST NEIGHBOR = " + sum );  
                List<Float> sortedDistances = new ArrayList(nearestNeighborDistanceStat);
                Collections.sort(sortedDistances);
                float median=sortedDistances.get(sortedDistances.size()/2);
                System.out.println("       MEDIAN DISTANCE TO NEAREST NEIGHBOR = " + median );  

                nearestNeighborDistanceStat.clear();
                System.exit(1);
             }
           }
      
            // We accumulate a new acceleration each time based on three rules
            void flock(ArrayList<Boid> boids) {
              PVector sep = separate(boids);   // Separation
              PVector ali = align(boids);      // Alignment
              PVector coh = cohesion(boids);   // Cohesion
              // The forces on flock can be weighted differently - if wanted, multiply them here with a weight to increase or decrease them
              sep.mult(1.0); 
              ali.mult(1.0);
              coh.mult(1.0);
              // Add the force vectors to acceleration
              applyForce(sep);
              applyForce(ali);
              applyForce(coh);
            }
          
            // Method to update location
            void update() {
              // Update velocity
              velocity.add(acceleration);
              // Limit speed
              velocity.limit(maxspeed);
              location.add(velocity);
              // Reset accelertion to 0 each cycle
              acceleration.mult(0);
            }
          
            // A method that calculates and applies a steering force towards a target
            // STEER = DESIRED MINUS VELOCITY
            PVector seek(PVector target) {
              PVector desired = PVector.sub(target, location);  // A vector pointing from the location to the target
              // Scale to maximum speed
              desired.normalize();
              desired.mult(maxspeed);
          
              // Above two lines of code below could be condensed with new PVector setMag() method
              // Not using this method until Processing.js catches up
              // desired.setMag(maxspeed);
          
              // Steering = Desired minus Velocity
              PVector steer = PVector.sub(desired, velocity);
              steer.limit(maxforce);  // Limit to maximum steering force
              return steer;
            }
             
            void render() {
              // Draw a triangle rotated in the direction of velocity
              float theta = velocity.heading2D() + radians(90);   
              fill(200, 100);
              stroke(255);
              pushMatrix();
              translate(location.x, location.y);
              rotate(theta);
              beginShape(TRIANGLES);
              vertex(0, -r*2);
              vertex(-r, r*2);
              vertex(r, r*2);
              endShape();
          
              // render avoidance radius as red circtle
              fill(#EE0000,  100);
              stroke(#FF0000);
              ellipse(0, 0, 2*minSeparation, 2*minSeparation);
          
              popMatrix();
            }
          
            // Wraparound
            void borders() {
              if (location.x < -r) location.x = width+r;
              if (location.y < -r) location.y = height+r;
              if (location.x > width+r) location.x = -r;
              if (location.y > height+r) location.y = -r;
            }
        
            // Separation
            // Method checks for nearby boids and steers away
            // it executes one of three versions, depending on config of the simulation
            PVector separate (ArrayList<Boid> boids) {
              if (separationType.equals( NEAREST) ) {
                return separateNearest(boids, 1);
              } else if (separationType.equals( NEAREST_SIX) ) {
                 return separateNearest(boids, 6);
              } else 
                return separateDefaultVersion(boids);
            }
            
            // version from processing website
            PVector separateDefaultVersion (ArrayList<Boid> boids) {
              float desiredseparation = 25.0f;
              PVector steer = new PVector(0, 0, 0);
              int count = 0;
              // For every boid in the system, check if it's too close
              for (Boid other : boids) {
                float d = PVector.dist(location, other.location);
                // If the distance is greater than 0 and less than an arbitrary amount (0 when you are yourself)
                if ((d > 0) && (d < desiredseparation)) {
                  // Calculate vector pointing away from neighbor
                  PVector diff = PVector.sub(location, other.location);
                  diff.normalize();
                  diff.div(d);        // Weight by distance
                  steer.add(diff);
                  count++;            // Keep track of how many
                }
              }
              // Average -- divide by how many
              if (count > 0) {
                steer.div((float)count);
              }
          
              // As long as the vector is greater than 0
              if (steer.mag() > 0) {
                // Implement Reynolds: Steering = Desired - Velocity
                steer.normalize();
                steer.mult(maxspeed);
                steer.sub(velocity);
                steer.limit(maxforce);
              }
              //System.out.println(" Steer= " + steer.toString());
              return steer;
            }
        
            // implement steering away according only to neigbhorsToTrack number of neighbors (ignoring others)
            // default version iterates through all neigbors, we ignore most of them!
            PVector separateNearest (ArrayList<Boid> boids, int neigborsToTrack) {
              PVector steer = new PVector(0, 0, 0);
              int count = 0;
              
              // calculate the distances to neighbors
              HashMap<Float, Boid> distances=new HashMap<Float, Boid>();
              for (Boid boid : boids) {
                float d = Math.abs(PVector.dist(location, boid.location));
                //System.out.println("       " + d );
                distances.put(d, boid);
              }
              
              // sort distances to get closest boids
              Set<Float> dists=distances.keySet();
              List<Float> sortedDistances = new ArrayList(dists);
              Collections.sort(sortedDistances);
              
              // For every boid in the system, check if it's too close
              for (int i=0; i<neigborsToTrack; i++) {
                Boid iThNearestBoid = distances.get(sortedDistances.get(i+1)); // get i-th nearest boid. zeroth is the same boid as the one we are processing
         
                float d = PVector.dist(location, iThNearestBoid.location);
                
                // If the distance is greater than 0 and less than an arbitrary amount (dist is 0 when you are yourself)
                if (d < minSeparation) {
                  // Calculate vector pointing away from neighbor
                  PVector diff = PVector.sub(location, iThNearestBoid.location);
                  diff.normalize();
                  
                  // push harder away if inside of no-acceptance zone
                  // change from default version!
                  diff.mult( (minSeparation-d)/minSeparation  );        
                  //System.out.println("    Push= " + diff.toString());
        
                  steer.add(diff);
                  count++;            // Keep track of how many
                }
              }
              // Average -- divide by how many nearest
              if (count > 0) {
                steer.div((float)count);
              }
        
              //System.out.println(" Steer before= " + steer.toString());
        
              // As long as the vector is greater than 0
              if (steer.mag() > 0) {
                // Implement Reynolds: Steering = Desired - Velocity
                steer.normalize();
                steer.mult(maxspeed);
                steer.sub(velocity);
                steer.limit(maxforce);
              }
              //System.out.println(" Steer after= " + steer.toString());
              return steer.mult( 1.0 + (float)Math.sqrt(boidNumber/10) ); //correction factor added, to counteract the gravity of large group of nodes
            }
          
          
            // Alignment
            // For every nearby boid in the system, calculate the average velocity
            PVector align (ArrayList<Boid> boids) {
              float neighbordist = 50;
              PVector sum = new PVector(0, 0);
              int count = 0;
              for (Boid other : boids) {
                float d = PVector.dist(location, other.location);
                if ((d > 0) && (d < neighbordist)) {
                  sum.add(other.velocity);
                  count++;
                }
              }
              if (count > 0) {
                sum.div((float)count);
                // First two lines of code below could be condensed with new PVector setMag() method
                // Not using this method until Processing.js catches up
                // sum.setMag(maxspeed);
          
                // Implement Reynolds: Steering = Desired - Velocity
                sum.normalize();
                sum.mult(maxspeed);
                PVector steer = PVector.sub(sum, velocity);
                steer.limit(maxforce);
                return steer;
              } 
              else {
                return new PVector(0, 0);
              }
            }
          
            // Cohesion
            // For the average location (i.e. center) of all nearby boids, calculate steering vector towards that location
            PVector cohesion (ArrayList<Boid> boids) {
              float neighbordist = 50;
              PVector sum = new PVector(0, 0);   // Start with empty vector to accumulate all locations
              int count = 0;
              for (Boid other : boids) {
                float d = PVector.dist(location, other.location);
                if ((d > 0) && (d < neighbordist)) {
                  sum.add(other.location); // Add location
                  count++;
                }
              }
              if (count > 0) {
                sum.div(count);
                return seek(sum);  // Steer towards the location
              } 
              else {
                return new PVector(0, 0);
              }
            }
          }
