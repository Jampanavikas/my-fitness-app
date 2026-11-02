package com.fittrack.app.data

import com.fittrack.app.data.entity.DayTask
import com.fittrack.app.data.entity.TaskCategory
import java.time.DayOfWeek

/**
 * "Lean & Athletic" weekly plan: Mon–Fri gym at 7 am (push / pull / legs /
 * upper volume / full body), Sat outdoor run, Sun full rest. Stretching after
 * every gym session, posture work every day, ~1,850 kcal / ~120 g protein diet.
 *
 * Exercise demo GIFs are served from the public-domain ExerciseGymGifsDB via
 * the jsDelivr CDN.
 */
object RoutineTemplate {

    /** Bump when the plan changes so today's and future days get regenerated. */
    const val PLAN_VERSION = 2

    const val CALORIE_TARGET = 1850
    const val PROTEIN_TARGET = 120
    const val WATER_TARGET_GLASSES = 12 // 3 L

    private const val GIF = "https://cdn.jsdelivr.net/gh/JahelCuadrado/ExerciseGymGifsDB@v1.1.0/"

    /** name, sets × reps (+ rest), how-to cues, demo gif path. */
    private data class Ex(val name: String, val reps: String, val how: String, val gif: String = "")

    private data class Block(val title: String, val warmup: Ex, val exercises: List<Ex>)

    private const val EFFORT = " Week 1: light weights, stop 3 reps before failure. After that, finish each set with 1–3 reps left and add 2.5 kg once you hit the top of the rep range on every set."

    private fun blockFor(day: DayOfWeek): Block? = when (day) {
        DayOfWeek.MONDAY -> Block(
            "Push: chest, shoulders, triceps",
            Ex("Warm-up: light bench press", "5–8 min walk/cycle, then 2 × 12 light",
                "Brisk walk or cycle 5–8 min, then 2 sets of 12 with just the bar to groove the movement.",
                GIF + "pectorals/barbell-bench-press.gif"),
            listOf(
                Ex("Barbell or machine bench press", "4 × 8–10 • rest 2 min",
                    "Feet flat, shoulder blades squeezed together. Lower the bar to mid-chest with elbows ~45°, press back up. Don't bounce off the chest.$EFFORT",
                    GIF + "pectorals/barbell-bench-press.gif"),
                Ex("Incline dumbbell press", "3 × 10–12 • rest 90 s",
                    "Bench at 30–45°. Press dumbbells up over the upper chest, lower slowly until you feel a stretch.",
                    GIF + "pectorals/dumbbell-incline-bench-press.gif"),
                Ex("Seated dumbbell shoulder press", "3 × 10 • rest 90 s",
                    "Back supported, dumbbells at shoulder height. Press overhead, lower under control. Abs tight — don't arch the lower back.",
                    GIF + "delts/dumbbell-seated-shoulder-press.gif"),
                Ex("Lateral raises", "3 × 12–15 • rest 60 s",
                    "Slight bend in the elbows, raise out to the sides to shoulder height leading with the elbows. Light weight, no swinging.",
                    GIF + "delts/dumbbell-lateral-raise.gif"),
                Ex("Triceps rope pushdown", "3 × 12 • rest 60 s",
                    "Elbows pinned to your sides. Push the rope down and spread the ends at the bottom, return slowly.",
                    GIF + "triceps/cable-pushdown-with-rope-attachment.gif"),
                Ex("Overhead triceps extension", "2 × 12 • rest 60 s",
                    "One dumbbell held with both hands overhead. Lower behind the head by bending the elbows, extend back up. Elbows point forward.",
                    GIF + "triceps/dumbbell-seated-triceps-extension.gif"),
                Ex("Plank", "3 × 30–45 s • rest 45 s",
                    "Forearms under shoulders, body in a straight line. Ribs down, glutes tight. Don't let the hips sag.",
                    GIF + "abs/weighted-front-plank.gif"),
            )
        )
        DayOfWeek.TUESDAY -> Block(
            "Pull: back, biceps, rear shoulders",
            Ex("Warm-up: light lat pulldown", "5–8 min walk/cycle, then 2 × 12 light",
                "Brisk walk or cycle 5–8 min, then 2 light sets of 12 lat pulldowns.",
                GIF + "lats/cable-lat-pulldown-full-range-of-motion.gif"),
            listOf(
                Ex("Lat pulldown", "4 × 8–10 • rest 2 min",
                    "Grip slightly wider than shoulders. Pull the bar to the upper chest, squeeze the shoulder blades down and back, return slowly. Don't lean back much.$EFFORT",
                    GIF + "lats/cable-lat-pulldown-full-range-of-motion.gif"),
                Ex("Seated cable row", "3 × 10 • rest 90 s",
                    "Chest up, slight knee bend. Pull the handle to your belly, squeeze the back, return with straight arms. No rocking.",
                    GIF + "upper-back/cable-seated-row.gif"),
                Ex("One-arm dumbbell row", "3 × 10 each • rest 90 s",
                    "Hand and knee on the bench, flat back. Pull the dumbbell to your hip, lower fully.",
                    GIF + "upper-back/dumbbell-one-arm-bent-over-row.gif"),
                Ex("Face pulls", "3 × 15 • rest 60 s",
                    "Rope at head height. Pull toward your face with elbows high and wide, squeeze the rear shoulders, return slowly.",
                    GIF + "delts/cable-standing-rear-delt-row-with-rope.gif"),
                Ex("Dumbbell or bar curl", "3 × 10 • rest 60 s",
                    "Elbows pinned to your sides. Curl up, squeeze at the top, lower slowly. No swinging.",
                    GIF + "biceps/dumbbell-biceps-curl.gif"),
                Ex("Hammer curl", "2 × 12 • rest 60 s",
                    "Palms facing each other, thumbs up. Curl and lower slowly.",
                    GIF + "biceps/dumbbell-hammer-curl.gif"),
                Ex("Hanging knee raise", "3 × 10 • rest 45 s",
                    "Hang from the bar (or use the captain's chair). Bring your knees up toward the chest by curling the pelvis, lower slowly. No swinging.",
                    GIF + "abs/assisted-hanging-knee-raise.gif"),
            )
        )
        DayOfWeek.WEDNESDAY -> Block(
            "Legs and glutes",
            Ex("Warm-up: squats + hip bridges", "Bodyweight squats 2 × 15, hip bridges 2 × 12",
                "Bodyweight squats: sit hips back and down, chest up, knees over toes. Hip bridges: drive through the heels and squeeze the glutes at the top.",
                GIF + "glutes/weighted-squat.gif"),
            listOf(
                Ex("Squat (goblet or barbell)", "4 × 8–10 • rest 2–3 min",
                    "Feet shoulder-width. Brace the abs, sit hips back and down until thighs are about parallel, drive up through the heels. Don't let the lower back over-arch.$EFFORT",
                    GIF + "quads/dumbbell-goblet-squat.gif"),
                Ex("Romanian deadlift", "3 × 10 • rest 2 min",
                    "Soft knees, push the hips back with a flat back, slide the bar down the thighs until you feel the hamstrings stretch, then stand up by squeezing the glutes.",
                    GIF + "glutes/barbell-romanian-deadlift.gif"),
                Ex("Leg press", "3 × 12 • rest 90 s",
                    "Feet shoulder-width on the platform. Lower until knees are ~90°, press back without locking the knees. Lower back stays on the pad.",
                    GIF + "glutes/sled-45-leg-press.gif"),
                Ex("Walking lunges", "3 × 10 each • rest 90 s",
                    "Long step forward, lower the back knee toward the floor, push through the front heel into the next step. Torso upright.",
                    GIF + "glutes/walking-lunge.gif"),
                Ex("Leg curl", "3 × 12 • rest 60 s",
                    "Curl your heels toward your glutes, pause, lower under control. Hips stay on the pad.",
                    GIF + "hamstrings/lever-lying-leg-curl.gif"),
                Ex("Standing calf raise", "4 × 15 • rest 45 s",
                    "Rise onto your toes as high as possible, pause, then lower the heels fully.",
                    GIF + "calves/lever-standing-calf-raise.gif"),
            )
        )
        DayOfWeek.THURSDAY -> Block(
            "Upper body volume: back, chest, arms",
            Ex("Warm-up: band pull-aparts + push-ups", "Band pull-aparts 2 × 15, push-ups 1 × 10",
                "Pull-aparts: arms straight, pull the band apart to chest level squeezing the shoulder blades. Then 10 controlled push-ups.",
                GIF + "delts/band-reverse-fly.gif"),
            listOf(
                Ex("Pull-ups (or assisted)", "3 × 6–10 • rest 2 min",
                    "Overhand grip shoulder-width. Pull your chest toward the bar, squeeze the shoulder blades, lower fully. Use the assisted machine or a band if needed.$EFFORT",
                    GIF + "lats/pull-up.gif"),
                Ex("Dips or machine chest press", "3 × 10 • rest 90 s",
                    "Dips: lean slightly forward, lower until upper arms are parallel to the floor, press up. Or use the chest press machine.",
                    GIF + "pectorals/chest-dip.gif"),
                Ex("Arnold press", "3 × 10 • rest 90 s",
                    "Start with palms facing you at chin height, rotate the palms forward as you press overhead, reverse on the way down.",
                    GIF + "delts/dumbbell-arnold-press.gif"),
                Ex("Cable fly", "3 × 12 • rest 60 s",
                    "Slight bend in the elbows. Bring the handles together in front of the chest in a hugging arc, open slowly.",
                    GIF + "pectorals/cable-standing-fly.gif"),
                Ex("Curl and pushdown superset", "3 × 12 each • rest 60 s",
                    "12 biceps curls straight into 12 triceps pushdowns, then rest. Elbows stay pinned on both.",
                    GIF + "biceps/barbell-curl.gif"),
                Ex("Lateral raises", "3 × 15 • rest 45 s",
                    "Light weight, raise out to the sides to shoulder height leading with the elbows.",
                    GIF + "delts/dumbbell-lateral-raise.gif"),
                Ex("Cable crunch", "3 × 12 • rest 45 s",
                    "Kneel holding the rope by your head. Crunch down by curling the spine (ribs toward hips), not by pulling with the arms.",
                    GIF + "abs/cable-kneeling-crunch.gif"),
            )
        )
        DayOfWeek.FRIDAY -> Block(
            "Full body and conditioning",
            Ex("Warm-up: light deadlift + lunges", "Light deadlift 2 × 8, then bodyweight lunges",
                "2 light sets of 8 deadlifts focusing on a flat back, then a set of bodyweight lunges.",
                GIF + "glutes/barbell-deadlift.gif"),
            listOf(
                Ex("Trap-bar or barbell deadlift", "3 × 6–8 • rest 2–3 min",
                    "Stand in the middle, hinge down with a flat back, grip, brace the abs, then push the floor away — hips and shoulders rise together.$EFFORT",
                    GIF + "glutes/trap-bar-deadlift.gif"),
                Ex("Dumbbell bench press or push-ups", "3 × 10–12 • rest 90 s",
                    "Press dumbbells up over the chest and lower slowly — or do push-ups with a straight body.",
                    GIF + "pectorals/dumbbell-bench-press.gif"),
                Ex("Bulgarian split squat", "3 × 8 each • rest 90 s",
                    "Back foot on a bench. Lower straight down until the front thigh is about parallel, push up through the front heel.",
                    GIF + "quads/dumbbell-single-leg-split-squat.gif"),
                Ex("Seated row", "3 × 10 • rest 90 s",
                    "Chest up, pull the handle to your belly, squeeze the back, return with straight arms.",
                    GIF + "upper-back/cable-seated-row.gif"),
                Ex("Farmer carry", "3 × 30 m • rest 60 s",
                    "Heavy dumbbells at your sides. Walk tall with short steps, shoulders back, abs braced.",
                    GIF + "quads/farmers-walk.gif"),
                Ex("Leg raise", "3 × 12 • rest 45 s",
                    "Lie flat, lower back pressed into the floor. Raise straight legs to 90°, lower slowly without touching down.",
                    GIF + "abs/lying-leg-raise-flat-bench.gif"),
                Ex("Finisher: incline walk or bike", "10 min • 30 s hard / 60 s easy",
                    "Alternate 30 seconds hard with 60 seconds easy on the incline treadmill or bike.",
                    GIF + "cardio/walking-on-incline-treadmill.gif"),
            )
        )
        else -> null
    }

    /** 5–8 min after every gym session. Hold 30 s, breathe, never bounce. */
    private const val HOLD = " Hold 30 s, breathe slowly, never bounce. Stretch until you feel a pull, not pain."
    private val STRETCHES = listOf(
        Ex("Kneeling hip flexor stretch", "30 s each side",
            "Half-kneel, squeeze the back-leg glute and shift the hips forward until you feel the front of the hip stretch.$HOLD",
            GIF + "quads/intermediate-hip-flexor-and-quad-stretch.gif"),
        Ex("Hamstring stretch", "30 s each leg",
            "Leg straight, hinge forward from the hips with a flat back.$HOLD",
            GIF + "hamstrings/hamstring-stretch.gif"),
        Ex("Standing quad stretch", "30 s each leg",
            "Pull your heel to your glute, knees together, hips pushed slightly forward.$HOLD",
            GIF + "quads/lying-side-quads-stretch.gif"),
        Ex("Calf stretch on a wall", "30 s each leg",
            "Hands on the wall, back leg straight with the heel down, lean in.$HOLD",
            GIF + "calves/calf-stretch-with-hands-against-wall.gif"),
        Ex("Doorway chest stretch", "30 s",
            "Forearm on the door frame at shoulder height, step through until the chest stretches.$HOLD",
            GIF + "pectorals/chest-and-front-of-shoulder-stretch.gif"),
        Ex("Child's pose (lats and back)", "30 s",
            "Kneel, sit back on your heels and reach your arms long in front.$HOLD",
            GIF + "lats/kneeling-lat-stretch.gif"),
        Ex("Cat-cow", "10 slow reps",
            "On all fours: round the back up (cat), then let the belly drop and look up (cow). Move slowly with your breath.",
            GIF + "spine/spine-stretch.gif"),
    )

    /** Anterior-pelvic-tilt fix — 10 min every day, including Sunday. */
    private val POSTURE = listOf(
        Ex("Glute bridge", "2 × 15",
            "Lie on your back, knees bent. Drive through the heels and squeeze the glutes at the top. Don't arch the lower back.",
            GIF + "glutes/low-glute-bridge-on-floor.gif"),
        Ex("Dead bug", "2 × 8 each side",
            "Arms up, knees at 90°. Lower opposite arm and leg slowly while keeping the lower back flat on the floor.",
            GIF + "abs/dead-bug.gif"),
        Ex("Bird dog", "2 × 8 each side",
            "On all fours, extend opposite arm and leg until level with your back, hold 2 s. Keep the hips square and back flat."),
        Ex("Posterior-tilt plank", "3 × 20–30 s",
            "Plank with the tailbone tucked under — ribs down, glutes tight, so the lower back is flat, not hollow.",
            GIF + "spine/standing-pelvic-tilt.gif"),
        Ex("Wall angels", "2 × 10",
            "Back, head and arms against the wall. Slide the arms up and down like a snow angel while your upper back stays on the wall."),
        Ex("Chin tucks", "2 × 10",
            "Look straight ahead and pull your chin straight back (make a double chin). Hold 2 s, relax."),
        Ex("Daily hip flexor stretch", "30 s each side",
            "Half-kneel, squeeze the back-leg glute and shift the hips forward.",
            GIF + "quads/intermediate-hip-flexor-and-quad-stretch.gif"),
    )

    /** Soya on Tue/Thu/Sat, chana + moong on the other days. */
    private fun lunchBowl(day: DayOfWeek) = when (day) {
        DayOfWeek.TUESDAY, DayOfWeek.THURSDAY, DayOfWeek.SATURDAY -> "Boiled soya bowl"
        else -> "Boiled chana + moong bowl"
    }

    fun buildDay(epochDay: Long, dayOfWeek: DayOfWeek): List<DayTask> {
        val tasks = mutableListOf<DayTask>()
        var order = 0
        fun add(t: DayTask) { tasks += t.copy(epochDay = epochDay, sortOrder = order++) }
        fun meal(title: String, sub: String, time: String, kcal: Int, protein: Int) =
            add(DayTask(epochDay = epochDay, category = TaskCategory.MEAL.name,
                title = title, subtitle = sub, time = time, kcal = kcal, protein = protein))
        fun ex(cat: TaskCategory, e: Ex, time: String = "") =
            add(DayTask(epochDay = epochDay, category = cat.name, title = e.name,
                subtitle = e.reps, time = time, howTo = e.how, imageUrl = e.gif))

        val gymDay = blockFor(dayOfWeek)

        meal("Before gym", "1 banana + black coffee, plus water", "06:15", 105, 1)

        if (gymDay != null) {
            ex(TaskCategory.WARMUP, gymDay.warmup, "07:00")
            gymDay.exercises.forEach { ex(TaskCategory.WORKOUT, it) }
            STRETCHES.forEach { ex(TaskCategory.STRETCH, it) }
            meal("After gym", "1 scoop whey in water", "08:30", 120, 25)
        } else if (dayOfWeek == DayOfWeek.SATURDAY) {
            ex(TaskCategory.SPORT, Ex("Outdoor run / jog", "5 min walk • run • 5 min walk",
                "Run at a pace where you can still talk.\n" +
                    "• Weeks 1–2: jog 1 min, walk 2 min × 8\n" +
                    "• Weeks 3–4: jog 2 min, walk 1 min × 8\n" +
                    "• Weeks 5–6: 20 min continuous easy jog\n" +
                    "• Week 7 on: 30–35 min easy jog\n" +
                    "Wear proper running shoes, drink water before and after, skip if unwell.",
                GIF + "cardio/run.gif"), "07:00")
        } else {
            add(DayTask(epochDay = epochDay, category = TaskCategory.REST.name,
                title = "Full rest", subtitle = "Sleep 8 h • optional 20 min walk + 10 min stretch",
                howTo = "Sleep 8 hours and nap if you need one. Optional relaxed 20 min walk and 10 min stretching. Plan your week and prep your boiled lunches."))
        }

        POSTURE.forEach { ex(TaskCategory.STRETCH, it) }

        meal("Lunch", "${lunchBowl(dayOfWeek)} + carrot, cucumber, lemon, chaat masala • dal, curd, salad, 2 rotis or small rice",
            "13:00", 650, 38)
        meal("3 boiled eggs", "With lunch or as a snack", "13:30", 210, 18)
        meal("4 pm snack", "Banana with oats and seeds", "16:00", 280, 9)
        meal("Night", "2 rotis + dal, egg or paneer + sabzi • 1 whole fruit (no juice)", "20:30", 500, 26)
        meal("Soak tomorrow's beans", "Soak overnight • boil at 100°C • skip fried food, sweets, cold drinks", "21:30", 0, 0)

        return tasks
    }
}
