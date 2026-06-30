package com.fittrack.app.data

import com.fittrack.app.data.entity.DayTask
import com.fittrack.app.data.entity.TaskCategory
import java.time.DayOfWeek

/**
 * Builds a day's checklist from the user's real routine:
 *  - 10am-7pm job, weekends off; morning walk + 2h learning before work
 *  - workout 7:45-8:45 pm with home equipment (barbell, dumbbells, tube
 *    resistance band, skipping rope, gripper)
 *  - meal plan (~1650 kcal target), pre-workout = peanut butter + banana + 2 eggs
 *
 * Exercise demo GIFs are served from the public-domain ExerciseGymGifsDB via
 * the jsDelivr CDN.
 */
object RoutineTemplate {

    const val CALORIE_TARGET = 1650
    const val PROTEIN_TARGET = 130
    const val WATER_TARGET_GLASSES = 14 // ~3.5 L

    private const val REPS = "4 sets × 12–15 reps"
    private const val GIF = "https://cdn.jsdelivr.net/gh/JahelCuadrado/ExerciseGymGifsDB@v1.1.0/"

    /** name, sets/reps, how-to cues, demo gif path. */
    private data class Ex(val name: String, val reps: String, val how: String, val gif: String = "")

    private data class Block(
        val warmup: Ex?,
        val exercises: List<Ex>,
        val category: TaskCategory = TaskCategory.WORKOUT,
        val headerTime: String = "19:45",
    )

    private val BURPEE_WARMUP = Ex(
        "Warm-up: Burpees", "20 reps × 4 sets",
        "Squat down, hands on floor, kick feet back to a plank, do one push-up, jump feet back in, then jump up with arms overhead. Keep core tight and land softly.",
        GIF + "cardio/burpee.gif"
    )

    private fun blockFor(day: DayOfWeek): Block = when (day) {
        DayOfWeek.MONDAY -> Block(
            warmup = BURPEE_WARMUP,
            exercises = listOf(
                Ex("Push-ups (chest)", REPS,
                    "Hands slightly wider than shoulders, body straight from head to heels. Lower your chest toward the floor with elbows ~45°, then push back up. Brace your core throughout.",
                    GIF + "pectorals/push-up.gif"),
                Ex("Dumbbell chest press", REPS,
                    "Lie on the floor/bench, dumbbells at chest level. Press up until arms are straight, lower slowly until upper arms touch the floor. Don't bounce.",
                    GIF + "pectorals/dumbbell-bench-press.gif"),
                Ex("Dumbbell shoulder press", REPS,
                    "Seated or standing, dumbbells at shoulder height palms forward. Press overhead until arms straighten, lower under control. Keep core braced, don't arch your lower back.",
                    GIF + "delts/dumbbell-seated-shoulder-press.gif"),
                Ex("Dumbbell lateral raises", REPS,
                    "Slight bend in elbows, raise dumbbells out to the sides up to shoulder height leading with the elbows, then lower slowly. Use light weight — no swinging.",
                    GIF + "delts/dumbbell-lateral-raise.gif"),
            )
        )
        DayOfWeek.TUESDAY -> Block(
            warmup = BURPEE_WARMUP,
            exercises = listOf(
                Ex("Pull-ups ★ most important", "4 sets × max reps",
                    "Overhand grip shoulder-width, hang with arms straight. Pull your chest toward the bar, squeeze shoulder blades, then lower fully. Loop the resistance band under your feet for assistance if needed.",
                    GIF + "lats/pull-up.gif"),
                Ex("Deadlift", "4 sets × 8–10 reps",
                    "Barbell over mid-foot. Hinge at the hips with a flat back, grip the bar, then drive through your heels to stand — hips and shoulders rise together. Keep the bar close to your legs.",
                    GIF + "glutes/barbell-deadlift.gif"),
                Ex("Barbell rows", REPS,
                    "Hinge forward ~45° with a flat back. Pull the barbell to your lower ribs, squeeze your back, lower slowly. Don't jerk with your lower back.",
                    GIF + "upper-back/barbell-bent-over-row.gif"),
                Ex("Rope face pull (band)", REPS,
                    "Anchor the tube resistance band at head height. Pull the handles toward your face with elbows high and wide, squeeze the rear delts, then return slowly.",
                    GIF + "delts/band-standing-rear-delt-row.gif"),
            )
        )
        DayOfWeek.WEDNESDAY -> Block(
            warmup = BURPEE_WARMUP,
            exercises = listOf(
                Ex("Biceps dumbbell curls", REPS,
                    "Elbows pinned to your sides, curl the dumbbells up, squeeze the biceps at the top, lower slowly. No swinging or using momentum.",
                    GIF + "biceps/dumbbell-biceps-curl.gif"),
                Ex("Biceps hammer curls", REPS,
                    "Neutral grip (palms facing each other), curl up keeping thumbs pointing up, lower slowly. Targets the brachialis and forearms.",
                    GIF + "biceps/dumbbell-hammer-curl.gif"),
                Ex("Triceps overhead extension", REPS,
                    "Hold one dumbbell with both hands overhead. Lower it behind your head by bending the elbows, then extend back up. Keep elbows pointing forward, not flaring out.",
                    GIF + "triceps/dumbbell-standing-triceps-extension.gif"),
                Ex("Dumbbell skull crushers", REPS,
                    "Lie down, dumbbells held over your chest. Bend the elbows to lower toward your forehead, then extend back up. Keep your elbows pointing at the ceiling.",
                    GIF + "triceps/dumbbell-lying-triceps-extension.gif"),
            )
        )
        DayOfWeek.THURSDAY -> Block(
            warmup = Ex("Warm-up: Squats", "20 reps × 4 sets",
                "Bodyweight squats — feet shoulder-width, sit hips back and down until thighs are about parallel, drive through your heels to stand. Chest up, knees tracking over toes.",
                GIF + "glutes/weighted-squat.gif"),
            exercises = listOf(
                Ex("Barbell squats", REPS,
                    "Bar resting on your upper back, feet shoulder-width. Sit hips back and down until thighs are about parallel, then drive through your heels back up. Keep chest up and knees tracking over toes.",
                    GIF + "glutes/barbell-full-squat.gif"),
                Ex("Dumbbell lunges", REPS,
                    "Dumbbells at your sides. Step forward and lower your back knee toward the floor, then push back up. Keep your torso upright and alternate legs.",
                    GIF + "glutes/dumbbell-lunge.gif"),
                Ex("Dumbbell leg curls", REPS,
                    "Lie face down, grip a dumbbell between your feet. Curl your heels toward your glutes, then lower under control. Targets the hamstrings.",
                    GIF + "hamstrings/dumbbell-lying-femoral.gif"),
                Ex("Calf raises", "4 sets × 15–20 reps",
                    "Stand on the edge of a step, hold dumbbells for resistance. Rise onto your toes as high as possible, pause, then lower your heels below the step level.",
                    GIF + "calves/dumbbell-standing-calf-raise.gif"),
            )
        )
        DayOfWeek.FRIDAY -> Block(
            warmup = Ex("Warm-up: Run + walk", "3 km run + 3 km walk",
                "Start with a brisk 10-minute walk, then alternate ~3 km of running with ~3 km of walking. Keep a pace where you can still breathe through your nose on the walk portions."),
            exercises = listOf(
                Ex("Crunches", "4 sets × 20 reps",
                    "Lie on your back, knees bent, hands by your head. Curl your shoulders off the floor toward your knees, squeeze your abs, then lower. Don't pull on your neck.",
                    GIF + "abs/crunch-floor.gif"),
                Ex("Leg raises", "4 sets × 15 reps",
                    "Lie flat, legs straight. Raise your legs to 90°, then lower slowly without touching the floor. Keep your lower back pressed into the ground.",
                    GIF + "abs/lying-leg-raise-flat-bench.gif"),
                Ex("Dragon-fly leg raises", "4 sets × 8–10 reps",
                    "Advanced: hold a fixed point behind your head. Raise your whole body straight up (hips lift off the floor), keeping it rigid, then lower slowly with control.",
                    GIF + "abs/reverse-crunch.gif"),
                Ex("Plank", "4 sets × 45–60 sec",
                    "Forearms under your shoulders, body in a straight line. Brace your core and glutes and hold. Don't let your hips sag or pike up.",
                    GIF + "abs/weighted-front-plank.gif"),
            )
        )
        DayOfWeek.SATURDAY -> Block(
            warmup = null,
            exercises = listOf(
                Ex("Sport / active play", "60–90 min",
                    "Play any sport you enjoy — badminton, football, cricket. Keep moving and have fun; this counts as your active recovery for the week."),
            ),
            category = TaskCategory.SPORT,
            headerTime = ""
        )
        DayOfWeek.SUNDAY -> Block(
            warmup = null,
            exercises = listOf(
                Ex("Walking", "40–60 min easy",
                    "Easy-paced walk to aid recovery and keep burning fat. No intensity — just steady movement and fresh air."),
            ),
            category = TaskCategory.REST,
            headerTime = ""
        )
    }

    fun buildDay(epochDay: Long, dayOfWeek: DayOfWeek): List<DayTask> {
        val tasks = mutableListOf<DayTask>()
        var order = 0
        fun add(t: DayTask) { tasks += t.copy(epochDay = epochDay, sortOrder = order++) }

        // --- Morning routine ---
        add(DayTask(epochDay = epochDay, category = TaskCategory.WALK.name,
            title = "Morning walk", subtitle = "30-40 min brisk • belly fat", time = "06:00"))
        add(DayTask(epochDay = epochDay, category = TaskCategory.LEARN.name,
            title = "Learning block", subtitle = "2 hours focused study", time = "06:30"))

        // --- Meals before workout ---
        add(DayTask(epochDay = epochDay, category = TaskCategory.MEAL.name,
            title = "Breakfast", subtitle = "4 boiled eggs + 1 banana", time = "07:00",
            kcal = 380, protein = 26))
        add(DayTask(epochDay = epochDay, category = TaskCategory.MEAL.name,
            title = "Mid-morning", subtitle = "Roasted chana (50g)", time = "10:00",
            kcal = 175, protein = 10))
        add(DayTask(epochDay = epochDay, category = TaskCategory.MEAL.name,
            title = "Lunch", subtitle = "Hostel rice + dal + sabzi", time = "13:00",
            kcal = 550, protein = 16))
        add(DayTask(epochDay = epochDay, category = TaskCategory.MEAL.name,
            title = "Pre-workout", subtitle = "Peanut butter (2 tbsp) + 1 banana + 2 eggs",
            time = "18:30", kcal = 435, protein = 22))

        // --- Workout block ---
        val block = blockFor(dayOfWeek)
        block.warmup?.let { w ->
            add(DayTask(epochDay = epochDay, category = TaskCategory.WARMUP.name,
                title = w.name, subtitle = w.reps, time = block.headerTime,
                howTo = w.how, imageUrl = w.gif))
        }
        block.exercises.forEach { ex ->
            add(DayTask(epochDay = epochDay, category = block.category.name,
                title = ex.name, subtitle = ex.reps, howTo = ex.how, imageUrl = ex.gif))
        }

        // --- Post-workout ---
        add(DayTask(epochDay = epochDay, category = TaskCategory.MEAL.name,
            title = "Dinner", subtitle = "3 egg bhurji + 2 bread + curd", time = "21:00",
            kcal = 450, protein = 28))
        add(DayTask(epochDay = epochDay, category = TaskCategory.MEAL.name,
            title = "Before bed", subtitle = "1 glass milk", time = "22:00",
            kcal = 120, protein = 6))

        return tasks
    }
}
