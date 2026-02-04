# Wordle
Wordle Stuff

Spanning: Is it possible to find five wordle words that do not share any letters (and so together span 25 out of the 26 letters in the alphabet)?
This question comes from a Stand-up Maths video, where he answered it using some python code. I decided to try my hand at writing some programs that could solve it too.
- My first attempt was using sets and graphs to try to be clever about the math. While I think it probably works, I haven't checked, because I ran it for a bit and was able to      estimate that the total runtime would be about 2-3 days.
- My second attempt was using BitSets and arrays. That worked a lot better. Total runtime is a couple seconds (5-10 million percent improvement).
