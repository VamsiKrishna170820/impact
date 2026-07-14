import React from "react";
import { render, screen, fireEvent, waitFor } from "@testing-library/react";
import { vi } from "vitest";
import "@testing-library/jest-dom";
import App from "../App";


describe("Pokemon Battle App", () => {

  beforeEach(() => {
    global.fetch = vi.fn();
  });


  /*
    Verify pokemon list API loads
    and pokemon names appear in dropdown
  */
  test("should load pokemon list and display pokemon options", async () => {

    fetch.mockResolvedValueOnce({
      ok: true,
      json: async () => [
        {
          id: 1,
          name: "Pikachu",
          hp: 100
        },
        {
          id: 2,
          name: "Charizard",
          hp: 150
        }
      ]
    });


    render(<App />);


    fireEvent.focus(
      screen.getByPlaceholderText("Search Challenger...")
    );


    await waitFor(() => {
      expect(
        screen.getByText("Pikachu")
      ).toBeInTheDocument();
    });

  });



  /*
    Verify selecting two pokemon
    enables battle button
  */
  test("should enable battle button after selecting two pokemon", async () => {

    fetch.mockResolvedValueOnce({
      ok: true,
      json: async () => [
        {
          id: 1,
          name: "Pikachu",
          hp: 100
        },
        {
          id: 2,
          name: "Charizard",
          hp: 150
        }
      ]
    });


    render(<App />);


    const playerOneInput =
      screen.getByPlaceholderText("Search Challenger...");


    fireEvent.focus(playerOneInput);


    await waitFor(() => {
      expect(
        screen.getByText("Pikachu")
      ).toBeInTheDocument();
    });


    fireEvent.click(
      screen.getByText("Pikachu")
    );


    const playerTwoInput =
      screen.getByPlaceholderText("Search Opponent...");


    fireEvent.focus(playerTwoInput);


    await waitFor(() => {
      expect(
        screen.getByText("Charizard")
      ).toBeInTheDocument();
    });


    fireEvent.click(
      screen.getByText("Charizard")
    );


    expect(
      screen.getByText("BATTLE!")
    ).not.toBeDisabled();

  });



  /*
    Verify successful battle
    calls attack API and stores battle result
  */
  test("should display battle result after successful battle", async () => {

    fetch
      .mockResolvedValueOnce({
        ok: true,
        json: async () => [
          {
            id: 1,
            name: "Pikachu",
            hp: 100
          },
          {
            id: 2,
            name: "Charizard",
            hp: 150
          }
        ]
      })
      .mockResolvedValueOnce({
        ok: true,
        json: async () => ({
          winner: "Pikachu",
          remainingHP: 50
        })
      });


    render(<App />);


    fireEvent.focus(
      screen.getByPlaceholderText("Search Challenger...")
    );


    await waitFor(() => {
      expect(
        screen.getByText("Pikachu")
      ).toBeInTheDocument();
    });


    fireEvent.click(
      screen.getByText("Pikachu")
    );


    fireEvent.focus(
      screen.getByPlaceholderText("Search Opponent...")
    );


    await waitFor(() => {
      expect(
        screen.getByText("Charizard")
      ).toBeInTheDocument();
    });


    fireEvent.click(
      screen.getByText("Charizard")
    );


    fireEvent.click(
      screen.getByText("BATTLE!")
    );


    await waitFor(() => {

      expect(fetch).toHaveBeenCalledWith(
        "http://localhost:8080/attack",
        expect.objectContaining({
          method: "POST",
          headers: {
            "Content-Type": "application/json"
          }
        })
      );

    });

  });



  /*
    Verify error message when battle API fails
  */
    test("should display error message when battle fails", async () => {

        fetch
          .mockResolvedValueOnce({
            ok: true,
            json: async () => [
              {
                id: 1,
                name: "Pikachu",
                hp: 100
              },
              {
                id: 2,
                name: "Charizard",
                hp: 150
              }
            ]
          })
          .mockResolvedValueOnce({
            ok: false,
            json: async () => ({
              message: "Pokemon not found"
            })
          });
      
      
        render(<App />);
      
      
        const playerOneInput =
          screen.getByPlaceholderText("Search Challenger...");
      
      
        fireEvent.focus(playerOneInput);
      
      
        await waitFor(() => {
          expect(
            screen.getByText("Pikachu")
          ).toBeInTheDocument();
        });
      
      
        fireEvent.click(
          screen.getByText("Pikachu")
        );
      
      
        const playerTwoInput =
          screen.getByPlaceholderText("Search Opponent...");
      
      
        fireEvent.change(
          playerTwoInput,
          {
            target: {
              value: "Unknown"
            }
          }
        );
      
      
        fireEvent.click(
          screen.getByText("BATTLE!")
        );
      
      
        await waitFor(() => {
          expect(
            screen.getByText((content) =>
              content.includes("Pokemon not found")
            )
          ).toBeInTheDocument();
        });
      
      });

});