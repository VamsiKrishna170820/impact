import React, { useState, useEffect, useRef } from "react";
import "./App.css";

import PokemonCard from "./components/PokemonCard";
import BattleResult from "./components/BattleResult";

export default function App() {
  const [pokemonList, setPokemonList] = useState([]);

  const [p1, setP1] = useState(null);
  const [p2, setP2] = useState(null);

  const [search1, setSearch1] = useState("");
  const [search2, setSearch2] = useState("");

  const [showDropdown1, setShowDropdown1] = useState(false);
  const [showDropdown2, setShowDropdown2] = useState(false);

  const [battleData, setBattleData] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [showWinnerModal, setShowWinnerModal] = useState(false);

  const dropdownRef1 = useRef(null);
  const dropdownRef2 = useRef(null);

  useEffect(() => {
    loadPokemonList();
  }, []);

  const loadPokemonList = async () => {
    try {
      setLoading(true);
      setError(null);

      const response = await fetch("http://localhost:8080/pokemons");

      if (!response.ok) {
        throw new Error("Failed to load pokemon list");
      }

      const data = await response.json();
      setPokemonList(data);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    const handleClickOutside = (event) => {
      if (
        dropdownRef1.current &&
        !dropdownRef1.current.contains(event.target)
      ) {
        setShowDropdown1(false);
      }

      if (
        dropdownRef2.current &&
        !dropdownRef2.current.contains(event.target)
      ) {
        setShowDropdown2(false);
      }
    };

    document.addEventListener("mousedown", handleClickOutside);

    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, []);

  const handleBattle = async () => {
    try {
      setLoading(true);
      setError(null);
      setBattleData(null);
      setShowWinnerModal(false);

      const response = await fetch("http://localhost:8080/attack", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify({
          pokemon1: search1,
          pokemon2: search2
        })
      });

      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.message);
      }

      const data = await response.json();

      setBattleData(data);
      setShowWinnerModal(true);
    } catch (err) {
      setError(err.message || "Battle failed");
    } finally {
      setLoading(false);
    }
  };

  const filtered1 = pokemonList.filter(
    (pokemon) =>
      pokemon.name &&
      pokemon.name.toLowerCase().includes(search1.toLowerCase())
  );

  const filtered2 = pokemonList.filter(
    (pokemon) =>
      pokemon.name &&
      pokemon.name.toLowerCase().includes(search2.toLowerCase())
  );

  let p1Hp = p1 ? p1.hp : 0;
  let p2Hp = p2 ? p2.hp : 0;

  if (battleData) {
    if (p1 && battleData.winner === p1.name) {
      p1Hp = battleData.remainingHP;
      p2Hp = 0;
    } else if (p2 && battleData.winner === p2.name) {
      p2Hp = battleData.remainingHP;
      p1Hp = 0;
    }
  }

  return (
    <div className="container">
      <header className="header">
        <h1>Oak's Pokémon Battle Ground</h1>
      </header>

      {error && (
        <div className="error">
          <span>⚠️ {error}</span>
          <button className="btn-retry" onClick={loadPokemonList}>
            Retry
          </button>
        </div>
      )}

      <div className="arena">

        <div className="card-container" ref={dropdownRef1}>
          <h2>Player 1</h2>

          <div className="search-box">
            <input
              type="text"
              placeholder="Search Challenger..."
              value={search1}
              onChange={(e) => {
                setSearch1(e.target.value);
                setShowDropdown1(true);
              }}
              onFocus={() => setShowDropdown1(true)}
              disabled={loading}
            />

            {showDropdown1 && filtered1.length > 0 && (
              <div className="dropdown">
                {filtered1.map((pokemon) => (
                  <div
                    key={pokemon.id}
                    className="dropdown-item"
                    onClick={() => {
                      setP1(pokemon);
                      setSearch1(pokemon.name);
                      setShowDropdown1(false);
                      setBattleData(null);
                    }}
                  >
                    {pokemon.name}
                  </div>
                ))}
              </div>
            )}
          </div>

          <PokemonCard pokemon={p1} currentHp={p1Hp} />
        </div>

        <div className="controls">
          <div className="vs">VS</div>

          <button
            className="btn-battle"
            disabled={!search1 || !search2 || loading}
            onClick={handleBattle}
          >
            BATTLE!
          </button>
        </div>

        <div className="card-container" ref={dropdownRef2}>
          <h2>Player 2</h2>

          <div className="search-box">
            <input
              type="text"
              placeholder="Search Opponent..."
              value={search2}
              onChange={(e) => {
                setSearch2(e.target.value);
                setShowDropdown2(true);
              }}
              onFocus={() => setShowDropdown2(true)}
              disabled={loading}
            />

            {showDropdown2 && filtered2.length > 0 && (
              <div className="dropdown">
                {filtered2.map((pokemon) => (
                  <div
                    key={pokemon.id}
                    className="dropdown-item"
                    onClick={() => {
                      setP2(pokemon);
                      setSearch2(pokemon.name);
                      setShowDropdown2(false);
                      setBattleData(null);
                    }}
                  >
                    {pokemon.name}
                  </div>
                ))}
              </div>
            )}
          </div>

          <PokemonCard pokemon={p2} currentHp={p2Hp} />
        </div>
      </div>

      <BattleResult
        battleData={battleData}
        show={showWinnerModal}
        close={() => setShowWinnerModal(false)}
      />
    </div>
  );
}