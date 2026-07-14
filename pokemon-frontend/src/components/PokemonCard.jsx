import React from "react";

export default function PokemonCard({ pokemon, currentHp }) {

    const getImageUrl = (name) => {

        if (!name) return "";
        const clean = String(name)
            .toLowerCase()
            .trim()
            .replace(/\s+/g, "-")
            .replace(/[^a-z0-9-]/g, "");

        return `https://img.pokemondb.net/sprites/home/normal/${clean}.png`;
    };

    if (!pokemon) {
        return (
            <div className="card">
                <div className="placeholder">
                    <div className="pokeball-placeholder"></div>
                    <span>
                        Select Challenger
                    </span>
                </div>
            </div>
        );
    }

    const hp = currentHp !== undefined 
        ? currentHp 
        : pokemon.hp;

    const hpPercentage = Math.max(0, (hp / pokemon.hp) * 100);

    return (
        <div className="card">
            <div className="card-body">
                <div className="sprite-box">
                    <img
                        src={getImageUrl(pokemon.name)}
                        alt={pokemon.name}
                    />
                </div>
                <h3>
                    {pokemon.name}
                </h3>
                <span className={`badge ${pokemon.type?.toLowerCase()}`}>
                    {pokemon.type}
                </span>

                <div className="hp-section">
                    <div className="hp-label">
                        <span>
                            Remaining HP
                        </span>
                        <span>
                            {Math.max(0, Math.round(hp))}
                            {/* " / " */}
                            {pokemon.hp}
                        </span>
                    </div>
                    <div className="progress-outer">
                        <div
                            className={
                                `progress-inner ${
                                    hpPercentage < 25
                                    ? "danger"
                                    : hpPercentage < 50
                                    ? "warning"
                                    : "safe"
                                }`
                            }
                            style={{
                                width: `${hpPercentage}%`
                            }}
                        />
                    </div>
                </div>
                <div className="stats">
                    <div>
                        <span>
                            ATK
                        </span>
                        {pokemon.attack}
                    </div>
                    <div>
                        <span>
                            DEF
                        </span>
                        {pokemon.defense}
                    </div>
                    <div>
                        <span>
                            SPD
                        </span>
                        {pokemon.speed}
                    </div>
                </div>
            </div>
        </div>
    );
}