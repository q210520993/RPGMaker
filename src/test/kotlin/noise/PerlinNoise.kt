import kotlin.math.floor

class PerlinNoise(seed: Long? = null) {
    private val permutation: List<Int>

    init {
        // Initialize permutation table based on seed (or random if seed is null)
        val rnd = seed?.let { java.util.Random(it) } ?: java.util.Random()
        permutation = (0..255).map { rnd.nextInt(256) }
    }

    fun noise(x: Double, y: Double, z: Double): Double {
        // Find unit cube that contains the point
        val X = floor(x).toInt() and 255
        val Y = floor(y).toInt() and 255
        val Z = floor(z).toInt() and 255

        // Relative position within the cube
        val x_rel = x - floor(x)
        val y_rel = y - floor(y)
        val z_rel = z - floor(z)

        // Compute fade curves for x, y, z
        val u = fade(x_rel)
        val v = fade(y_rel)
        val w = fade(z_rel)

        // Hash coordinates of the 8 cube corners
        val A = permutation[X] + Y
        val AA = permutation[A] + Z
        val AB = permutation[A + 1] + Z
        val B = permutation[X + 1] + Y
        val BA = permutation[B] + Z
        val BB = permutation[B + 1] + Z

        // Blend results from 8 corners of cube
        val result = lerp(w,
            lerp(v,
                lerp(u, grad(permutation[AA], x_rel, y_rel, z_rel),
                    grad(permutation[BA], x_rel - 1, y_rel, z_rel)),
                lerp(u, grad(permutation[AB], x_rel, y_rel - 1, z_rel),
                    grad(permutation[BB], x_rel - 1, y_rel - 1, z_rel))),
            lerp(v,
                lerp(u, grad(permutation[AA + 1], x_rel, y_rel, z_rel - 1),
                    grad(permutation[BA + 1], x_rel - 1, y_rel, z_rel - 1)),
                lerp(u, grad(permutation[AB + 1], x_rel, y_rel - 1, z_rel - 1),
                    grad(permutation[BB + 1], x_rel - 1, y_rel - 1, z_rel - 1))))

        return result
    }

    private fun fade(t: Double): Double {
        return t * t * t * (t * (t * 6 - 15) + 10)
    }

    private fun lerp(t: Double, a: Double, b: Double): Double {
        return a + t * (b - a)
    }

    private fun grad(hash: Int, x: Double, y: Double, z: Double): Double {
        val h = hash and 15
        val u = if (h < 8) x else y
        val v = if (h < 4) y else if (h == 12 || h == 14) x else z
        return (if (h and 1 == 0) u else -u) + (if (h and 2 == 0) v else -v)
    }

}