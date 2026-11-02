# FitTrack 💪

A personal fitness, diet & routine tracker for Android — a **beautiful, animated, glassy** UI, fully **offline**, your data only. No login, no server, no internet required (except to stream exercise demo GIFs).

Built with **Kotlin + Jetpack Compose** (Material 3) and **Room** for local persistence.

---

## ✨ Features

### Today tab
- Animated lava-lamp **glassmorphism** background
- **Calorie ring** (target 1,850 kcal) that fills as you check off meals
- **Protein** progress bar (target 120 g) and a day-completion bar
- **Water tracker** (3 L / 12 glasses)
- Auto-generated **daily checklist** built from the *Lean & Athletic* weekly plan:
  - Gym at **7 am** Mon–Fri: warm-up, exercises with sets × reps and rest times
  - **Stretching** after every gym session, **posture correction** every day
  - Meals with times, calories and protein
- Tap any item to tick it done — saved locally, survives restarts
- Each exercise has a **"How to do it"** panel with an **animated demo GIF** + form cues, and a **Watch demo** link (YouTube search)
- Changing the plan (`RoutineTemplate.PLAN_VERSION`) rebuilds today and future days; past history is kept

### Workout split
| Day | Warm-up | Focus |
|-----|---------|-------|
| Mon | Light bench press | Push: chest, shoulders, triceps |
| Tue | Light lat pulldown | Pull: back, biceps, rear shoulders |
| Wed | Bodyweight squats + hip bridges | Legs and glutes |
| Thu | Band pull-aparts + push-ups | Upper body volume: back, chest, arms |
| Fri | Light deadlift + lunges | Full body + conditioning finisher |
| Sat | 5 min walk | Outdoor run / jog (progresses week by week) |
| Sun | — | Full rest |

**After every gym session:** hip flexor, hamstring, quad, calf, chest stretches, child's pose, cat-cow.
**Every day:** glute bridge, dead bug, bird dog, posterior-tilt plank, wall angels, chin tucks, hip flexor stretch.

### Diet (~1,850 kcal · ~120 g protein)
| Time | Meal |
|------|------|
| 6:15 am | 1 banana + black coffee |
| After gym | 1 scoop whey in water |
| 1 pm | Boiled bowl (soya Tue/Thu/Sat, chana + moong other days) + dal, curd, salad, 2 rotis or small rice · 3 boiled eggs |
| 4 pm | Banana with oats and seeds |
| 8:30 pm | 2 rotis + dal, egg or paneer + sabzi · 1 whole fruit |
| 9:30 pm | Soak tomorrow's beans |

### Dashboard tab
- **Weekly completion** bar chart (Mon–Sun, today highlighted)
- Stat cards: average completion, day streak, workouts done, tasks done
- **Daily reminders** master toggle

### Notifications
- Daily reminders: before gym, gym, after gym, meals, posture work, soaking beans
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
