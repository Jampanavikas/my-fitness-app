# FitTrack 💪

A personal fitness, diet & routine tracker for Android — a **beautiful, animated, glassy** UI, fully **offline**, your data only. No login, no server, no internet required (except to stream exercise demo GIFs).

Built with **Kotlin + Jetpack Compose** (Material 3) and **Room** for local persistence.

---

## ✨ Features

### Today tab
- Animated lava-lamp **glassmorphism** background
- **Calorie ring** (target 1,650 kcal) that fills as you check off meals
- **Protein** progress bar (target 130 g) and a day-completion bar
- **Water tracker** (~3.5 L / 14 glasses)
- Auto-generated **daily checklist** built from your routine:
  - Morning walk + 2-hour learning block (before the 10–7 job)
  - 6 meals with times, calories and protein
  - Workout **7:45–8:45 pm**, split by weekday
- Tap any item to tick it done — saved locally, survives restarts
- Each exercise has a **"How to do it"** panel with an **animated demo GIF** + form cues

### Workout split
| Day | Warm-up | Focus |
|-----|---------|-------|
| Mon | Burpees | Chest + Shoulders |
| Tue | Burpees | Back / Pull (pull-ups ★, deadlift, rows, band face-pull) |
| Wed | Burpees | Arms (biceps + triceps) |
| Thu | Squats  | Legs |
| Fri | Run + walk | Core + Cardio |
| Sat | — | Sport / active play |
| Sun | — | Easy walking |

*Equipment: barbell, dumbbells, tube resistance band, skipping rope, gripper.*

### Dashboard tab
- **Weekly completion** bar chart (Mon–Sun, today highlighted)
- Stat cards: average completion, day streak, workouts done, tasks done
- **Daily reminders** master toggle

### Notifications
- Daily reminders at each routine time (walk, learning, meals, workout)
- Exact alarms that survive Doze and reboot; re-arm automatically each day

---

## 🚀 Run it

### Android Studio
1. **Open** the project folder in Android Studio.
2. Let Gradle sync (downloads dependencies the first time).
3. Pick an emulator or device and press **Run** ▶.

### Command line
```bash
export JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home"
./gradlew :app:assembleDebug      # build app/build/outputs/apk/debug/app-debug.apk
./gradlew :app:installDebug       # install on a connected device/emulator
```

---

## 🛠 Stack
- minSdk 26 · targetSdk/compileSdk 36 · AGP 8.9 · Kotlin 2.1 · Gradle 8.11
- Jetpack Compose (Material 3), Room, DataStore, Navigation Compose
- [Coil](https://coil-kt.github.io/coil/) for animated GIFs
- AlarmManager + BroadcastReceiver for reminders

## 🗺 Roadmap
- Diet / Workout-detail / Progress (weight chart toward 80 → 72 kg) / Profile screens
- Per-set weight × reps logging
- **Phase 2:** AI features (smart meal suggestions, adaptive plans)

---

*Personal project — built for my own routine.*
