% % % % % % % % % % % % % % % % % % % % % % % % % % % % % % % % % % %
% % Copyright (C) 2014, Akshay Asthana, all rights reserved.
% %  * Do not redistribute without permission.
% %  * Strictly for academic and non-commerial purpose only.
% %  * Use at your own risk.
% % 
% % Please cite the follwing paper if you use this code:
% %  * Incremental Face Alignment in the Wild.
% %    A. Asthana, S. Zafeiriou, S. Cheng and M. Pantic.
% %    In CVPR 2014.
% % 
% % Contact
% % akshay.asthana@gmail.com
% % For details refer to https://sites.google.com/site/chehrahome/ 
% % % % % % % % % % % % % % % % % % % % % % % % % % % % % % % % % %
clear; close all;
addpath(genpath('.'));

% % Load Models
fitting_model='models/Chehra_f1.0.mat';
load(fitting_model);    

% % Test Path
image_path='test_images/';
img_list=dir([image_path,'*.jpg']);
nothing = 0;
results = fopen('results.txt','wt');

% % Perform Fitting 
for i=1:1%size(img_list,1)
    filename = strcat(int2str(5),'.txt');
    % % Load Image
    test_image=im2double(imread([image_path,int2str(i),'.jpg']));
    %test_image= imresize(test_image,0.3);
    imshow(test_image);hold on;
    
    disp(['Detecting Face in ' ,int2str(i),'.jpg']);
    faceDetector = vision.CascadeObjectDetector();
    bbox = step(faceDetector, test_image);
    tic;
    if ~isempty(bbox)
        test_init_shape = InitShape(bbox,refShape);
        test_init_shape = reshape(test_init_shape,49,2);
        %plot(test_init_shape(:,1),test_init_shape(:,2),'ro');
        
  
    
        if size(test_image,3) == 3
            test_input_image = im2double(rgb2gray(test_image));
        else
            test_input_image = im2double((test_image));
        end
    
        disp(['Fitting ' img_list(i).name]);    
    % % Maximum Number of Iterations 
    % % 3 < MaxIter < 7
        MaxIter=6;
        test_points = Fitting(test_input_image,test_init_shape,RegMat,MaxIter);
    
        %disp(test_points);
        
        %dlmwrite(filename,test_points,'delimiter','\t');
    plot(test_points(:,1),test_points(:,2),'g*','MarkerSize',6);hold off;
    %legend('Initialization','Final Fitting');
    %set(gcf,'units','normalized','outerposition',[0 0 1 1]);
    else
       
        %dlmwrite(filename,nothing);
    end
     time = toc;
     result(i) = time;
     
    %pause;
    close all;
    
end
    result = reshape(result,[100,1]);
    dlmwrite('Time(scaled).xls',result);
