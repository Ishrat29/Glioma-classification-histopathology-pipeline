# Histopathology Image Classification Pipeline

This repository contains a portion of an end-to-end deep learning pipeline for
classifying histopathology whole-slide image (WSI) tiles, covering patch
extraction, data splitting, stain normalization, augmentation, and model
training/evaluation with explainability (XAI).

> **Note on scope:** This repository includes the data preparation,
> preprocessing, and explainability components that I personally authored.
> The core model architecture and training/testing scripts used in the
> related research project are part of ongoing institutional work and are
> not included here for confidentiality reasons. The training notebook is
> provided to show pipeline orchestration and the explainability (CAM-based)
> workflow.

## Pipeline Overview

```
1. Patch Extraction → 2. Data Split → 3/4. Normalization → 5. Augmentation → 6. Training & XAI
```

## Files

| File | Description |
|---|---|
| `patch_extraction.groovy` | QuPath script — extracts fixed-size tiles from whole-slide images based on annotated ROIs |
| `data_split.ipynb` | Stratified 5-fold train/validation/test split, preserving class balance |
| `stain_normalization.ipynb` | Reinhard stain normalization using TIAToolbox |
| `mixed_normalization.ipynb` | Blends Reinhard + Macenko normalized images via weighted combination |
| `augmentation.ipynb` | Fixed-count image augmentation (rotation, zoom, flipping) using Augmentor |
| `training_and_xai.ipynb` | Training/testing orchestration + explainability via Grad-CAM, Grad-CAM++, SmoothGrad-CAM++, Score-CAM |

## Requirements

```
torch
torchvision
numpy
pandas
scikit-learn
scikit-image
opencv-python
Pillow
matplotlib
Augmentor
tiatoolbox
timm
```

## Usage

Each notebook/script contains placeholder paths in the form `<PLACEHOLDER>`.
Replace these with your own local paths before running. Files are numbered
in pipeline order, though each stage can also be run independently if you
already have the appropriate intermediate data.

## License

This code is shared for portfolio/demonstration purposes. Please contact me
before reuse in academic or commercial work.
