/**
 * QuPath Groovy Script — Tile/Patch Extraction
 *
 * Splits each annotated Region of Interest (ROI) in the current image into
 * fixed-size, non-overlapping tiles and exports them as PNG files.
 * Intended to run inside QuPath (supports "Run for project").
 *
 * Update OUTPUT_DIRECTORY and class labels before running.
 */

import qupath.lib.images.servers.LabeledImageServer
import qupath.lib.roi.RoiTools
import qupath.lib.roi.RectangleROI
import qupath.lib.objects.PathAnnotationObject
import qupath.lib.gui.scripting.QPEx
import qupath.lib.objects.classes.PathClassFactory
import java.awt.Color

// ---- Configuration ----
def OUTPUT_DIRECTORY = "<OUTPUT_DIRECTORY>"
def TILE_SIZE = 1000          // tile size in pixels
def DOWNSAMPLE = 1.0          // export resolution (1.0 = full resolution)

// ---- Setup ----
def imageData = getCurrentImageData()
def name = GeneralTools.getNameWithoutExtension(imageData.getServer().getMetadata().getName())
def server = getCurrentServer()

mkdirs(OUTPUT_DIRECTORY)

def annotObjects = getAnnotationObjects()

for (def counter = 0; counter < annotObjects.size(); counter++) {
    print counter
    def roi = annotObjects[counter].getROI()

    int left_x = roi.getBoundsX()
    int left_y = roi.getBoundsY()
    int roi_width = roi.getBoundsWidth()
    int roi_height = roi.getBoundsHeight()

    for (def x = left_x; x + TILE_SIZE <= left_x + roi_width; x += TILE_SIZE) {
        for (def y = left_y; y + TILE_SIZE <= left_y + roi_height; y += TILE_SIZE) {

            def rect_roi = new RectangleROI(x, y, TILE_SIZE, TILE_SIZE)
            def request = RegionRequest.createInstance(server.getPath(), DOWNSAMPLE, rect_roi)

            // Optional: labeled mask server for paired segmentation masks.
            // Update class names/labels as needed for your dataset.
            def labelServer = new LabeledImageServer.Builder(imageData)
                .backgroundLabel(0, ColorTools.BLACK)
                .downsample(DOWNSAMPLE)
                .addLabel('Class_A', 1, ColorTools.RED)
                .addLabel('Class_B', 2, ColorTools.GREEN)
                .multichannelOutput(false)
                .build()

            new TileExporter(imageData)
                .region(request)
                .downsample(DOWNSAMPLE)
                .imageExtension('.png')
                .tileSize(TILE_SIZE)
                .annotatedTilesOnly(true)
                .overlap(0)
                .writeTiles(OUTPUT_DIRECTORY)
        }
    }
}

print 'Patch extraction done.'
