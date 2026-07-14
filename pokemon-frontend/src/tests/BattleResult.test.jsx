/**
 * Description: Unit tests for BattleResult component.
 * Expected Outcome: Verifies conditional rendering, winner details,
 * image generation, HP formatting and close button functionality.
 */

import { describe, test, expect, vi } from "vitest";
import { render, screen, fireEvent } from "@testing-library/react";
import "@testing-library/jest-dom/vitest";
import BattleResult from "../components/BattleResult";

describe("BattleResult", () => {

    const battleData = {
        winner: "Pikachu",
        remainingHP: 45.8
    };

    test("should not render when show is false", () => {

        render(
            <BattleResult
                show={false}
                battleData={battleData}
                close={vi.fn()}
            />
        );

        expect(screen.queryByText(/Battle Complete!/i)).not.toBeInTheDocument();

    });

    test("should not render when battleData is null", () => {

        render(
            <BattleResult
                show={true}
                battleData={null}
                close={vi.fn()}
            />
        );

        expect(screen.queryByText(/Battle Complete!/i)).not.toBeInTheDocument();

    });

    test("should render winner details correctly", () => {

        render(
            <BattleResult
                show={true}
                battleData={battleData}
                close={vi.fn()}
            />
        );

        expect(screen.getByText(/Battle Complete!/i)).toBeInTheDocument();
        expect(screen.getByText("Pikachu Wins!")).toBeInTheDocument();
        expect(screen.getByText(/Remaining HP:/i)).toBeInTheDocument();
        expect(screen.getByText(/46/)).toBeInTheDocument();
        expect(screen.getByRole("button", { name: "OK" })).toBeInTheDocument();

    });

    test("should generate the correct image url", () => {

        render(
            <BattleResult
                show={true}
                battleData={battleData}
                close={vi.fn()}
            />
        );

        const image = screen.getByAltText("Pikachu");

        expect(image).toHaveAttribute(
            "src",
            "https://img.pokemondb.net/sprites/home/normal/pikachu.png"
        );

    });

    test("should display zero when remaining HP is negative", () => {

        render(
            <BattleResult
                show={true}
                battleData={{
                    winner: "Pikachu",
                    remainingHP: -10
                }}
                close={vi.fn()}
            />
        );

        expect(screen.getByText(/0/)).toBeInTheDocument();

    });

    test("should call close function when OK button is clicked", () => {

        const close = vi.fn();

        render(
            <BattleResult
                show={true}
                battleData={battleData}
                close={close}
            />
        );

        fireEvent.click(screen.getByRole("button", { name: "OK" }));

        expect(close).toHaveBeenCalledTimes(1);

    });

});