# BBM 412 - COMPUTER GRAPHICS – 2026 FALL

# TERM PROJECT GUIDE

> **Please make sure to read the whole document carefully before starting to prepare your project proposal.**

Term Project is realized by a group of **3 students\***.

\* *Possible to make this 3 or 4 students depending on how large or small the final class size will be after add-drop.*

As your term project, you and your teammates will develop a **WebGL game** on the given theme.

This semester’s theme is:

## AI & Games – Learning to Play

You will request one of the predefined project ideas from the list given below. Assignments will be **first-come-first-served**.

---

## 🧩 Classic Board & Puzzle Games

- **AI Tic-Tac-Toe (in 3D)** – Minimax-powered 3D tic-tac-toe.
- **Connect Four With a Brain** – A WebGL Connect Four where the AI plays optimally (or with adjustable difficulty).
- **Checkers With Strategy** – Classic checkers in 3D with AI opponent logic.
- **Puzzle Solver Bot** – Player designs sliding puzzles or mazes; AI demonstrates solving step-by-step.
- **Simon Says AI** – AI generates increasingly complex sequences of moves or lights, which the player must repeat.

## 🗺️ Pathfinding & Navigation

- **Maze Runner AI** – Create a 3D maze and an AI agent that uses pathfinding (A\* or Dijkstra) to escape.
- **Chase the Treasure** – Player and AI agents compete to reach a randomly placed treasure in a 3D arena.
- **Pathfinding Visualizer Game** – Player places walls/obstacles; the AI must dynamically recalculate the shortest path.
- **Obstacle Course Racer** – AI agents navigate a procedurally generated obstacle course; the player competes in speed.
- **Capture the Flag** – Two teams (AI vs. player, or AI vs. AI) compete to capture each other’s flag in a 3D arena.

## 🐺 Chasing, Fleeing & Swarm Behaviors

- **Predator–Prey Arena** – Simulate predators and prey using simple chase/evade behaviors.
- **Flocking Frenzy** – Implement boids (flocking algorithm) where the player can disrupt or guide a flock’s movement.
- **Pac-Man Reinvented** – Pac-Man-style game where ghosts use different AI strategies (random, greedy, predictive).
- **Hide and Seek** – AI-controlled seekers must find hiders that use simple evasion strategies in a 3D environment.
- **Snake with AI Opponents** – Classic Snake game where multiple AI snakes compete with the player for food.

## ⚔️ Competitive Arena & Battle Games

- **Rock–Paper–Scissors Battle Bots** – 3D agents “fight” using strategies that evolve over time (reinforcement learning or scripted patterns).
- **Tower Defense AI** – Enemies follow paths toward a goal; player builds towers, while AI adapts attack strategies.
- **Soccer Bots** – Simulate a mini soccer field with AI-controlled bots that chase and shoot a ball into goals.
- **Swarm vs. Player** – The player defends against a swarm of AI drones that coordinate their attacks.
- **King of the Hill** – Multiple AI agents compete to control a zone; the player can try to outsmart them.

---

# Minimum CG Requirements

The minimum CG requirements that every submitted project **MUST** meet:

In addition to the requirements given above, every submitted project must also meet the minimum CG criteria given as follows.

1. The project is realized in **WebGL2.x** using **Javascript, GLSL and HTML**. You need to produce your own code. You must **NOT** use game engines (such as Unity) to produce your WebGL application.

2. The project is realized completely in **3D** (not in 2D or 2.5D). You are free to use perspective or parallel projection (or both, by enabling the option to switch between the two) for 3D viewing.

3. The game must incorporate a **scoring scheme** and show the current score during the game.

4. The user is able to translate and rotate the camera in 3 dimensions (**6 DOF in total**).

5. There are at least **3 different objects in different morphologies (\~shapes)**. For example, `{apple, pear, banana}` or `{car, truck, motorcycle}` are groups of objects with elements in varying morphologies.

6. At least **3 objects** (again in different morphologies) — may be the same objects as above — should be logically movable (e.g. one of these objects should not be a wall), and can be selected, translated and rotated freely in 3 dimensions by the user.

   Selection of the objects can be done by:

   - mouse picking,
   - keyboard key,
   - interface,
   - touch control,
   - joystick,
   - chopstick,
   - wizard’s staff,
   - or telekinesis.

   The last three get bonus points.

7. There must be at least:

   - one **directional light**, and
   - one **spotlight**.

   These two lights can be turned off at the beginning if you want.

   The spotlight should be separate from the camera (it should not move with the camera).

   The user will be able to:

   - translate and rotate the spotlight in 3 dimensions (**6 DOF in total**) freely,
   - turn the spotlight on/off anytime,
   - rotate the directional light in 3 dimensions freely,
   - turn the directional light on/off anytime,
   - adjust the strength (**intensity**) of the two lights as desired.

8. Axis selection of the translations and rotations of the camera, objects and the light source can be done by keyboard buttons or via a user interface.

9. At least **2 different types of shading** should be implemented using at least **2 shader programs** with different pairs of vertex and fragment shaders.

   These 2 shader programs **MUST** be implemented by the project group and their GLSL source codes must exist as separate text files:

   - at least 1 vertex shader source file for each shader program,
   - at least 1 fragment shader source file for each shader program,
   - therefore at least **4 GLSL files in total**.

   The user should be able to switch between the different shading options at any time.

   Please also note that these 2 shader programs should have significantly different shader code pairs (`vertex + fragment`) and have significantly different effects on the rendered scene.

   In other words, the difference between the resulting renderings of the two programs should **NOT** be trivial.

10. The two mandatory shader sets must modify the **entire rendered view captured by the camera**, not just specific parts or objects.

11. To better understand this requirement, please refer to the example from a previous year's project here: **YouTube Link**.

12. Of course, additional shaders can act on only the specific parts/objects in the scene.

13. **Distinctly Different Outputs:** The two mandatory shader sets should produce significantly different visual effects. Again, the example above is an excellent illustration of this point.

14. You must include your shader codes (**at least 2 pairs**) in your presentation and explain their mechanism briefly.

15. In a separate part of the main 3D scene, the names of the project group members are assembled using **3D objects that are congruent with the selected project**.

    Using a keyboard shortcut:

    - the camera will move (**not teleport**) to this part of the scene,
    - show the names from a top-down view,
    - then, using the same shortcut, return to where it was before.

16. Pressing **h/H** will bring up the help menu, which describes all the shortcuts and how to use the application in detail.

    Pressing **h/H** again will hide the menu.

---

A project that satisfies all of the requirements above will start with a **35 points final demo base score out of 100**.

---

# Additional Scoring

In addition to this base score:

- finalizing the project successfully as detailed in the proposal document,
- extra features,
- high quality and appealing scene/environment design,
- level of organization and task distribution among the project group,
- presentation skills and the quality of the submitted presentation document,

will raise the score up to **100 points (and beyond)**.

---

# Term Project Breakdown

## ~~Project Proposal — 10%~~

## Final Presentation + Final Demo — 85%

### Minimum CG Requirements — 35%

### Presentation — 15%

This includes:

- Fair Share of The Presentation Time between All Group Members
- Members' Individual Depth of Knowledge of the Project's Details
- Ability to Answer Any Question Regarding the Project
- Presentation Delivery and Elocution
- Overall Quality of the Presentation
- Effective Use of Time

### Demo Quality + Additional Overall CG Quality Score — 30%

### Advanced CG Features — 20+%

Features that excel beyond regular additional quality.

Examples include:

- tessellation shaders,
- geometry shaders,
- reflection mapping,
- bump mapping,
- transparency,
- particle systems,
- hierarchical modeling,
- procedural animation,
- physics simulation,
- fluid simulation,
- wind simulation,
- and other advanced features that have not been implemented in 414 experiments.

## Demo Video (Teaser/Trailer) — 15%

### How Informative The Video Is About The Project — 50%

For example, describing project features using narration and/or text/captions.

### Video Sound and Image Quality — 25%

> Hoping for 4K Dolby Digital but will settle for 720p+ stereo.

### Game-Trailer-Likeness — 25%

You may see a sample final presentation grading sheet at the link below:

<https://docs.google.com/spreadsheets/d/e/2PACX-1vTbH_fqBJwby0tvhGmtbhWrR287-QVpEp8f0j3U912hHJ19pWETIa_NxHZWYI9rvOQ07NeInfd4Nzn3/pubhtml?gid=574811193&single=true>

---

# NOTE

You may make use of resources such as books, websites, sample codes on the web, etc. by **explicitly referencing each resource at the presentation**.

However, if the submitted project is a duplicate (or a modified version) of a previous work by others or the group's own members, members of the project group will:

- fail the class, and
- be reported for disciplinary action.

---

# At Project Delivery

The following must be submitted:

1. A directory containing:

   - the presentation document (`.ppt`, `.pptx` or `.pdf` file),
   - the demo video.

2. The entire **NetBeans project directory**, cleaned from unnecessary files.

These **2 separate directories** must be compressed together as a single **ZIP or RAR** file and submitted to the online system before the submission deadline.

---

Final presentations will take place during the **2nd week of the finals period**.

---

# External Resources and Libraries

As long as your project meets all the minimum requirements, you may make use of **any (yes, any!) external library** you want to use.

However, you must mention the references for those external libraries in your final presentation.

You may make use of any external:

- model,
- texture,
- animation,
- etc.

made by a third party.

However, making your own:

- models,
- animations,
- textures,
- etc.

using tools such as:

- Blender,
- Maya,
- GIMP,
- Photoshop,

will gain more **Additional Overall Quality** score.

This is why project groups need to be meticulous about shaping project aspects.

You should also mind that you need to create and use your **own vertex + fragment shaders** or everything goes to bust, i.e., **0**.

---

# ~~PROJECT PROPOSAL FORMAT~~

~~The format for the project proposal is to include the following:~~

- ~~**Project Name**~~
- ~~**Project Group Members**~~
  - ~~name,~~
  - ~~student number,~~
  - ~~email address~~
- ~~**Short description of the proposed application**~~
  - ~~approximately **50–100 words**~~
  - ~~non-technical~~
- ~~**Detailed Project Proposal**~~
  - ~~approximately **300–500 words**~~
  - ~~be as technical as possible~~
  - ~~use terms and methods from the Computer Graphics context~~
- ~~**Visual drafts (görsel taslaklar)** of the proposed project:~~
  - ~~interfaces,~~
  - ~~scenes,~~
  - ~~environment,~~
  - ~~characters,~~
  - ~~etc.~~
- ~~**References** that will be made use of.~~

---

# PROJECT PRESENTATION FORMAT

- Presentations are made in **English**.
- All group members share the presentation time in approximately equal durations.
- All group members should master the details of the entire project and be able to answer any question regarding:
  - the relevant theoretical background,
  - the implementation.
- The entire presentation including the **video + demo** should not exceed **15 minutes**.
- Please use your 15 minutes wisely.
- Please make at least a couple of rehearsals as a group beforehand.

For example, you may divide the 15 minutes as:

- **8 minutes** — presentation from the presentation document
- **2–3 minutes** — video + demo
- remaining time — **Questions & Answers**

> The presentation will be stopped if it exceeds the **10-minute mark**, and the group will be asked to start the video + demo.

You should **not** describe how you achieved the minimum requirements, as they will be obvious to everyone.

You **MUST** describe how you achieved:

- extra features,
- advanced / bonus features, if any.

Do **not** show programming code directly.

Instead, show:

- algorithms, or
- summarized pseudocode.
