import os
import shutil

# Source folder
source_folder = "images"

# Destination folder
destination_folder = "JPG_Files"

# Create destination folder if it does not exist
if not os.path.exists(destination_folder):
    os.makedirs(destination_folder)

# Get all files from the source folder
files = os.listdir(source_folder)

# Move all .jpg files
for file in files:
    if file.lower().endswith(".jpg"):
        source_path = os.path.join(source_folder, file)
        destination_path = os.path.join(destination_folder, file)

        shutil.move(source_path, destination_path)
        print(f"Moved: {file}")

print("===== TASK COMPLETED =====")
print("All JPG files have been moved successfully.")