import cv2

# Read an image from a file
image = cv2.imread('image.png')

# Resize the image
resized_image = cv2.resize(image, (100, 100)) # Resize the image to 100x100 pixels

# Rotate the image
(rows, cols) = image.shape[:2] # Get the number of rows and columns in the original image
center = (cols // 2, rows // 2) # Calculate the center point of the image
rotation_matrix = cv2.getRotationMatrix2D(center, 45, 1) # Create a rotation matrix for a 45-degree rotation around the center point
rotated_image = cv2.warpAffine(image, rotation_matrix, (cols, rows))  # Apply the rotation to the original image

# Flip the image
flipped_image = cv2.flip(image, 1) # 0 for vertical flip, 1 for horizontal flip, -1 for both

# Display the images
cv2.imshow('Original', image)
cv2.imshow('Resized', resized_image)
cv2.imshow('Rotated', rotated_image)
cv2.imshow('Flipped', flipped_image)
cv2.waitKey(0) # Wait for a key press
cv2.destroyAllWindows() # Close all windows