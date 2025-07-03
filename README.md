# 🖼️ TammyShutterfly Canvas

![Kotlin](https://img.shields.io/badge/language-Kotlin-blue)
![Jetpack Compose](https://img.shields.io/badge/Jetpack_Compose-%F0%9F%92%96-5f2eea)
![Status](https://img.shields.io/badge/status-Prototype-orange)

A Jetpack Compose project that lets users drag and drop stickers into a canvas, freely pan/zoom them, and undo/redo changes — all in a smooth, touch-gesture experience.

---

## 📌 Overview

This is a Kotlin + Compose experiment for interactive gesture-based image placement and manipulation.

**Key Features:**
- Drag stickers from a carousel into a canvas
- Pan and zoom each image independently
- Undo/redo support for drop & transform
- Clear canvas functionality

Built with:
- ✅ Jetpack Compose
- ✅ Kotlin
- ✅ Mutable state and gesture detection
- ✅ Undo/Redo Manager using a command pattern
- 🚧 Experimental APIs (see caveats below)

---

## ⚠️ Experimental API Usage

This project uses **Compose experimental APIs**, including:

- `HorizontalUncontainedCarousel`
- `dragAndDropSource` / `dragAndDropTarget`

These APIs worked well together to enable drag-and-drop with minimal setup and excellent performance.

While they wouldn’t be used in **production code** due to stability concerns, they were suitable for this prototype and allowed for rapid iteration under time constraints.

---

## 🔁 Undo / Redo Logic

The UndoRedoManager is built using a command pattern:

CanvasAction interface defines execute() and undo() methods.

Each action — like dropping a sticker or transforming it — is wrapped in a command.

Undo/redo stacks store these actions as discrete, reversible units.

| Action Type | Undoable? | Details |
|-------------|-----------|---------|
| Drop        | ✅        | Removes the image from canvas |
| Pan/Zoom    | ✅        | Tracks changes during gesture |


### 🔁 Pan/Zoom Undo Strategy

Pan/zoom is treated as **one atomic action**:

- When a user places their fingers on an image (`onGestureStart`), the **initial scale/offset** is recorded.
- When the gesture ends, the **final scale/offset** is saved.
- This allows us to undo the entire transformation as a single step.

---

### 🧪 Testing (TODO)

Testing is still in progress. Current plans:

- Refactored `UndoRedoManager` to be more modular and testable.
- Move `CanvasAction` command classes away from referencing `CanvasViewModel` directly — ideally pass in a minimal interface or just a data list.
- Otherwise create a mock ViewModel or fake image list for unit testing commands in isolation.

---

### 📡 Notes on Data Layer Abstraction

Currently, the carousel date is static — but the code is structured to easily support swapping the data source in the repository (server, database, etc). To adapt:

- Add `UiState.Loading`, `Success`, and `Error` wrappers for fetching sticker data
- Replace the local image list with a repository-backed data source
- Reuse the existing abstraction boundaries to inject different data layers easily

---

### 🔤 Additional Improvements (Future Work)

- Move hardcoded strings to resource files for localization support
- Support image rotation as an extra gesture mode
- Add long-press delete gesture on canvas images
- Add stronger vibration on long press before dragging

---


