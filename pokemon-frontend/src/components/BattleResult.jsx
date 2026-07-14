import React from "react";

export default function BattleResult({battleData, show, close}) {

    if (!show || !battleData) {

        return null;

    }
    const getImageUrl = (name) => {

        if (!name) return "";
        const clean = String(name)
            .toLowerCase()
            .trim()
            .replace(/\s+/g, "-")
            .replace(/[^a-z0-9-]/g, "");
        return `https://img.pokemondb.net/sprites/home/normal/${clean}.png`;

    };

    return (
        <div className="modal-overlay">
            <div className="winner-modal-card">
                <h3>
                    🏆 Battle Complete! 🏆
                </h3>
                <div className="winner-modal-body">
                    <img
                        src={getImageUrl(battleData.winner)}
                        alt={battleData.winner}
                    />
                    <h2>
                        {battleData.winner} Wins!
                    </h2>
                    <div className="winner-hp-text">
                        Remaining HP:
                        {" "}
                        {Math.max(0, Math.round(battleData.remainingHP))}
                    </div>
                </div>

                <button className="btn-close-modal" onClick={close}>
                    OK
                </button>
            </div>
        </div>
    );
}