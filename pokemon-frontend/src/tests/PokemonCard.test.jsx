/**
 * Description: Unit tests for PokemonCard component.
 * Expected Outcome: Verifies placeholder rendering, Pokémon details,
 * remaining HP display, and image generation.
 */

import { describe, test, expect } from "vitest";
import { render, screen } from "@testing-library/react";
import "@testing-library/jest-dom/vitest";
import PokemonCard from "../components/PokemonCard";

describe("PokemonCard", () => {

    const pokemon = {
        name: "Pikachu",
        type: "Electric",
        hp: 100,
        attack: 55,
        defense: 40,
        speed: 90
    };


    // Verify placeholder when Pokemon is not selected
    // Expected Outcome: Select Challenger message should display
    test("should render placeholder when pokemon is not provided", () => {

        render(<PokemonCard pokemon={null} />);

        expect(screen.getByText("Select Challenger")).toBeInTheDocument();
        expect(screen.queryByRole("img")).not.toBeInTheDocument();

    });


    // Verify Pokemon details are displayed
    // Expected Outcome: Name, type, stats and HP should render
    test("should render pokemon details correctly", () => {

        render(<PokemonCard pokemon={pokemon} />);

        expect(screen.getByText("Pikachu")).toBeInTheDocument();
        expect(screen.getByText("Electric")).toBeInTheDocument();
        expect(screen.getByText("Remaining HP")).toBeInTheDocument();

        const hpSection = screen.getByText("Remaining HP").parentElement;

        expect(hpSection).toHaveTextContent("100100");
        expect(screen.getByText("55")).toBeInTheDocument();
        expect(screen.getByText("40")).toBeInTheDocument();
        expect(screen.getByText("90")).toBeInTheDocument();

    });


    // Verify current HP value is displayed
    // Expected Outcome: Remaining HP should update with current HP
    test("should display current HP when currentHp is provided", () => {

        render(
            <PokemonCard
                pokemon={pokemon}
                currentHp={65}
            />
        );

        expect(screen.getByText("Remaining HP")).toBeInTheDocument();
        const hpSection = screen.getByText("Remaining HP").parentElement;
        expect(hpSection).toHaveTextContent("65100");

    });


    // Verify image URL generation
    // Expected Outcome: Correct Pokemon image should render
    test("should generate correct pokemon image url", () => {

        render(
            <PokemonCard pokemon={pokemon} />
        );

        const image = screen.getByAltText("Pikachu");

        expect(image).toHaveAttribute("src",expect.stringContaining("pikachu.png"));

    });

});



