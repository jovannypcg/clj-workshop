(ns clj-workshop-2020.data-modeling.x01-data-solution)

(def hero-data
  [{:name    "Batman"
    :alias   "Bruce Wayne"
    :powers  #{"Rich"}
    :weapons #{"Utility Belt" "Kryptonite Spear"}
    :nemesis [{:name "Joker"}
              {:name "Penguin"}]}
   {:name    "Superman"
    :alias   "Clark Kent"
    :powers  #{"Strength" "Flight" "Bullet Immunity"}
    :nemesis [{:name "Lex Luthor"}
              {:name "Zod"}
              {:name "Faora"}]}
   {:name    "Wonder Woman"
    :alias   "Diana Prince"
    :powers  #{"Strength" "Flight"}
    :weapons #{"Lasso of Truth" "Bracers"}
    :nemesis [{:name "Ares"}]}
   {:name    "Shazam"
    :alias   "Billy Batson"
    :powers  #{"Strength" "Bullet Immunity"}
    :nemesis [{:name "Dr. Thaddeus Sivana"}
              {:name "Pride"}
              {:name "Envy"}
              {:name "Greed"}
              {:name "Wrath"}
              {:name "Sloth"}
              {:name "Gluttony"}
              {:name "Lust"}]}
   {:name    "Joker"
    :alias   "Jack Nieper"
    :nemesis [{:name "Batman"}]}])

(def family-data
  [{:name    "Petter Parker"
    :gender  "M"
    :parents [{:name "Richard Parker"} {:name "Mary Parker"}]
    :alias   ["Spider-Man" "Spidey"]}
   {:name     "Richard Parker"
    :gender   "M"
    :spouse   {:name "Mary Parker"}
    :siblings [{:name "Ben Parker"}]}
   {:name   "Mary Parker"
    :gender "F"
    :spouse {:name "Richard Parker"}}
   {:name     "Ben Parker"
    :gender   "M"
    :spouse   {:name "May Parker"}
    :siblings [{:name "Richard Parker"}]}
   {:name   "May Parker"
    :gender "F"
    :spouse {:name "Ben Parker"}}])
