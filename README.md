# AMIQT-FaceDetectionApp
Face detection, pose estimation and landmark localization in the wild. 

found a 2.0 version 

compared with v1.0:
- include a mew model
- does not show the model at the beginning every time we compile

tested two of the provided models 

face_p99

pros:
- the speed is acceptable

cons:
- not accurate in detecting faces
- not accurate in detecting landmarks when the some parts of the face are blocked 

face_new

pros:
- more accurate in detecting faces
- more accurate in detecting landmarks, 

cons:
- slower than face_p99 (could be a lot slower in certain images)
